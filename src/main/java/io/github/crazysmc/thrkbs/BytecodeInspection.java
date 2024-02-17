package io.github.crazysmc.thrkbs;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.util.Bytecode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
}
