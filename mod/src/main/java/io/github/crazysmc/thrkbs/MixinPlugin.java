package io.github.crazysmc.thrkbs;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.util.Bytecode;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin
{
  @Override
  public void onLoad(String mixinPackage)
  {
    AnnotationProcessor.init();
  }

  @Override
  public String getRefMapperConfig()
  {
    return null;
  }

  @Override
  public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
  {
    return true;
  }

  @Override
  public void acceptTargets(Set<String> myTargets, Set<String> otherTargets)
  {
  }

  @Override
  public List<String> getMixins()
  {
    return null;
  }

  @Override
  public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
  {
  }

  @Override
  public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
  {
    for (MethodNode method : targetClass.methods)
      for (AbstractInsnNode instruction : method.instructions)
      {
        if (!(instruction instanceof MethodInsnNode))
          continue;
        String name = ((MethodInsnNode) instruction).name;
        if (!"isKeyDown".equals(name) && !name.startsWith("intIfEqual$"))
          continue;
        Object constant = Bytecode.getConstant(instruction.getPrevious());
        if (constant instanceof Integer)
          PotentialKeyBinding.found((Integer) constant);
      }
  }
}
