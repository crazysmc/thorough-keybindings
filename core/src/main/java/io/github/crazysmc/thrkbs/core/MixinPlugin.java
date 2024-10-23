package io.github.crazysmc.thrkbs.core;

import io.github.crazysmc.thrkbs.AnnotationProcessor;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.util.Bytecode;

import java.util.List;
import java.util.Set;

import static io.github.crazysmc.thrkbs.core.MappingRegistry.MAPPING_REGISTRY;

public class MixinPlugin implements IMixinConfigPlugin
{
  private static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings/Mixin");
  private static final MappingResolver RESOLVER = FabricLoader.getInstance().getMappingResolver();

  private static final MethodInsnNode IS_KEY_DOWN =
      // com/mojang/blaze3d/platform/InputConstants.isKeyDown (JI)Z
      fromIntermediary("net.minecraft.class_3675", "method_15987", "(JI)Z");

  private final MappingRegistry mappingRegistry = MAPPING_REGISTRY;

  private static MethodInsnNode fromIntermediary(String owner, String name, String descriptor)
  {
    name = RESOLVER.mapMethodName("intermediary", owner, name, descriptor);
    owner = RESOLVER.mapClassName("intermediary", owner).replace('.', '/');
    return new MethodInsnNode(0, owner, name, descriptor);
  }

  @Override
  public void onLoad(String mixinPackage)
  {
    AnnotationProcessor.registerInjectionPoints();
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

  private boolean match(MethodInsnNode a, MethodInsnNode b)
  {
    return a.owner.equals(b.owner) && a.name.equals(b.name) && a.desc.equals(b.desc);
  }

  private void acceptMethodInsn(MethodInsnNode instruction)
  {
    if (!match(IS_KEY_DOWN, instruction) && !instruction.name.startsWith("intIfEqual$"))
      return;
    Object constant = Bytecode.getConstant(instruction.getPrevious());
    if (!(constant instanceof Integer))
      return;
    int i = (Integer) constant;
    LOGGER.debug(() -> String.format("Found key constant %1$d (0x%1$02X) at %2$s", i, instruction.name));
    if (mappingRegistry.registerKeyCode(i))
      LOGGER.trace("Registered key code {} at method call", i);
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
      LOGGER.debug(() -> String.format("Found key constant %1$d (0x%1$02X) as table switch case", constant));
      if (mappingRegistry.registerKeyCode(constant))
        LOGGER.trace("Registered key code {} at table switch", constant);
    }
  }

  private void acceptLookupSwitchInsn(LookupSwitchInsnNode instruction)
  {
    if (!instruction.keys.contains((int) 'A'))
      return;
    for (int constant : instruction.keys)
    {
      LOGGER.debug(() -> String.format("Found key constant %1$d (0x%1$02X) as lookup switch case", constant));
      if (mappingRegistry.registerKeyCode(constant))
        LOGGER.trace("Registered key code {} at lookup switch", constant);
    }
  }
}
