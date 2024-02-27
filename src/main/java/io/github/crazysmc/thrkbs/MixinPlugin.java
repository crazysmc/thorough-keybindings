package io.github.crazysmc.thrkbs;

import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqualInjectionInfo;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin
{
  private final MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
  private final String minecraft = resolver.mapClassName("intermediary", "net.minecraft.unmapped.C_8105098");

  @Override
  public void onLoad(String mixinPackage)
  {
    InjectionInfo.register(ModifyIntIfEqualInjectionInfo.class);
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
    BytecodeInspection.acceptClassNode(targetClass);
    if (minecraft.equals(targetClassName))
      BytecodeInspection.acceptMinecraftClassNode(targetClass);
  }

  @Override
  public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo)
  {
  }
}
