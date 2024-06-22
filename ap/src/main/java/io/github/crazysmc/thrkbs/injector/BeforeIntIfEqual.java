package io.github.crazysmc.thrkbs.injector;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

import java.util.ArrayList;
import java.util.Collection;

@AtCode(namespace = "THRKBS", value = "CONSTANT_IF_ICMPNE")
public class BeforeIntIfEqual extends BeforeConstant
{
  public BeforeIntIfEqual(IMixinContext context, AnnotationNode node, String returnType)
  {
    super(context, node, returnType);
  }

  public BeforeIntIfEqual(InjectionPointData data)
  {
    super(data);
  }

  @Override
  public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes)
  {
    ArrayList<AbstractInsnNode> list = new ArrayList<>();
    if (!super.find(desc, insns, list))
      return false;
    boolean found = false;
    for (AbstractInsnNode insn : list)
    {
      AbstractInsnNode next = insn.getNext();
      if (next != null)
        switch (next.getOpcode())
        {
          case Opcodes.ILOAD:
            next = next.getNext();
            if (next == null || next.getOpcode() != Opcodes.IADD)
              break;
            next = next.getNext();
            if (next == null || next.getOpcode() != Opcodes.IF_ICMPNE)
              break;
          case Opcodes.IF_ICMPNE:
            nodes.add(insn);
            found = true;
        }
    }
    return found;
  }
}
