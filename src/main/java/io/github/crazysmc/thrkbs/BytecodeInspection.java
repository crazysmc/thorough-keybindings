package io.github.crazysmc.thrkbs;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.util.Bytecode;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class BytecodeInspection
{
  private static final Map<String, Function<AbstractInsnNode, AbstractInsnNode>> MAP = new Object2ObjectArrayMap<>(2);

  static
  {
    MAP.put("getEventKey", AbstractInsnNode::getNext);
    MAP.put("isKeyDown", AbstractInsnNode::getPrevious);
  }

  public static void acceptClassNode(ClassNode classNode)
  {
    for (MethodNode methodNode : classNode.methods)
      for (AbstractInsnNode insn : methodNode.instructions)
        if (insn instanceof MethodInsnNode)
          Optional.ofNullable(MAP.get(((MethodInsnNode) insn).name)).ifPresent(function -> {
            Object constant = Bytecode.getConstant(function.apply(insn));
            if (constant instanceof Integer)
              PotentialKeyBinding.found((Integer) constant);
          });
  }
}
