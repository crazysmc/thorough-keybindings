package io.github.crazysmc.thrkbs;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.util.Bytecode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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
      if ("()V".equals(methodNode.desc))
      {
        boolean keyboard = false;
        boolean const256 = false;
        Integer int256 = 256;
        for (AbstractInsnNode instruction : methodNode.instructions)
        {
          if (!keyboard && instruction instanceof MethodInsnNode)
          {
            MethodInsnNode invoke = (MethodInsnNode) instruction;
            keyboard = "org/lwjgl/input/Keyboard".equals(invoke.owner) &&
                "next".equals(invoke.name) &&
                "()Z".equals(invoke.desc);
          }
          if (keyboard && int256.equals(Bytecode.getConstant(instruction)))
            const256 = true;
          if (const256)
            handleInstruction(instruction);
        }
      }
      else if (methodNode.access == Opcodes.ACC_PRIVATE && "(I)Z".equals(methodNode.desc))
        for (AbstractInsnNode instruction : methodNode.instructions)
          handleInstruction(instruction);
  }

  private static void handleInstruction(AbstractInsnNode instruction)
  {
    if (instruction.getOpcode() == Opcodes.ILOAD && ((VarInsnNode) instruction).var == 1)
      Optional.ofNullable(Bytecode.getConstant(instruction.getNext()))
          .filter(Integer.class::isInstance)
          .map(Integer.class::cast)
          .ifPresent(PotentialKeyBinding::found);
  }
}
