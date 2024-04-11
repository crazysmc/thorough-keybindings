package io.github.crazysmc.thrkbs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.util.Bytecode;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin
{
  public static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings|Mixin");

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
    LOGGER.debug("Inspecting class {} for keybindings", targetClassName);
    for (MethodNode method : targetClass.methods)
      for (AbstractInsnNode instruction : method.instructions)
        if (instruction instanceof MethodInsnNode)
          acceptMethodInsn((MethodInsnNode) instruction);
        else if (instruction instanceof TableSwitchInsnNode)
          acceptTableSwitchInsn((TableSwitchInsnNode) instruction);
        else if (instruction instanceof LookupSwitchInsnNode)
          acceptLookupSwitchInsn((LookupSwitchInsnNode) instruction);
  }

  private void acceptMethodInsn(MethodInsnNode instruction)
  {
    if (!"isKeyPressed".equals(instruction.name) && // FIXME
        !"getKey".equals(instruction.name) &&
        !instruction.name.startsWith("intIfEqual$"))
      return;
    LOGGER.warn(instruction.name);
    Object constant = Bytecode.getConstant(instruction.getPrevious());
    if (!(constant instanceof Integer))
      return;
    int i = (Integer) constant;
    LOGGER.debug(String.format("Found key constant %1$d (0x%1$02X) at %2$s", i, instruction.name));
    PotentialKeyBinding.found(i);
  }

  private void acceptTableSwitchInsn(TableSwitchInsnNode instruction)
  {
    if (instruction.min != 'A')
      return;
    Label defaultLabel = instruction.dflt.getLabel();
    for (int i = 0, size = instruction.labels.size(); i < size; i++)
    {
      if (defaultLabel.equals(instruction.labels.get(i).getLabel()))
        continue;
      int constant = instruction.min + i;
      LOGGER.debug(String.format("Found key constant %1$d (0x%1$02X) as switch case", constant));
      PotentialKeyBinding.found(constant);
    }
  }

  private void acceptLookupSwitchInsn(LookupSwitchInsnNode instruction)
  {
    for (int key : instruction.keys)
    {
      LOGGER.debug(String.format("Found key constant %1$d (0x%1$02X) as switch case", key));
      PotentialKeyBinding.found(key);
    }
  }
}
