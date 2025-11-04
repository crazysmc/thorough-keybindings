package io.github.crazysmc.thrkbs.version;

import com.google.common.collect.ImmutableMap;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.*;

import static io.github.crazysmc.thrkbs.ThoroughKeybindings.MC_VERSION;
import static io.github.crazysmc.thrkbs.Versions.V20W06A;

public class MixinPlugin implements IMixinConfigPlugin
{
  private final Map<String, Boolean> map = ImmutableMap.of(
      "io.github.crazysmc.thrkbs.version.mixin.shared.KeyboardHandlerMixinProfiler", MC_VERSION.compareTo(V20W06A) >= 0
  );

  @Override
  public void onLoad(String mixinPackage)
  {
  }

  @Override
  public String getRefMapperConfig()
  {
    return null;
  }

  @Override
  public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
  {
    return map.getOrDefault(mixinClassName, true);
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
  }
}
