package io.github.crazysmc.thrkbs;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.util.Bytecode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static io.github.crazysmc.thrkbs.ThoroughKeybindings.LOGGER;

public class BytecodeInspection
{
  private static final Map<String, Function<AbstractInsnNode, AbstractInsnNode>> MAP = new HashMap<>(4);

  static
  {
    MAP.put("getEventKey", AbstractInsnNode::getNext);
    MAP.put("isKeyDown", AbstractInsnNode::getPrevious);
  }

  public static void acceptClassNode(ClassNode classNode)
  {
    for (MethodNode methodNode : classNode.methods)
      for (AbstractInsnNode instruction : methodNode.instructions)
        if (instruction instanceof MethodInsnNode)
          Optional.ofNullable(MAP.get(((MethodInsnNode) instruction).name))
              .map(function -> function.apply(instruction))
              .map(Bytecode::getConstant)
              .filter(Integer.class::isInstance)
              .map(Integer.class::cast)
              .ifPresent(PotentialKeyBinding::found);
  }

  public static void acceptMinecraftClassNode(ClassNode classNode)
  {
    for (MethodNode methodNode : classNode.methods)
      if (methodNode.access == Opcodes.ACC_PUBLIC && "()V".equals(methodNode.desc))
      {
        boolean keyboard = false;
        boolean const256 = false;
        Integer int256 = 256;
        for (AbstractInsnNode instruction : methodNode.instructions)
        {
          if (!keyboard && instruction instanceof LdcInsnNode && "keyboard".equals(((LdcInsnNode) instruction).cst))
            keyboard = true;
          if (keyboard && int256.equals(Bytecode.getConstant(instruction)))
            const256 = true;
          int opcode = instruction.getOpcode();
          assert opcode != Opcodes.ILOAD || instruction instanceof VarInsnNode : instruction;
          if (const256 && opcode == Opcodes.ILOAD && ((VarInsnNode) instruction).var == 1)
            Optional.ofNullable(Bytecode.getConstant(instruction.getNext()))
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .ifPresent(PotentialKeyBinding::found);
        }
      }
  }
}
