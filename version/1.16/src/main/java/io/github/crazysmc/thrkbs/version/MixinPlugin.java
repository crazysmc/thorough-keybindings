package io.github.crazysmc.thrkbs.version;

import com.google.common.collect.ImmutableMap;
import io.github.crazysmc.thrkbs.BaseMixinPlugin;

import java.util.Map;

import static io.github.crazysmc.thrkbs.ThoroughKeybindings.MC_VERSION;
import static io.github.crazysmc.thrkbs.Versions.V116PRE1;

public class MixinPlugin extends BaseMixinPlugin
{
  private final Map<String, Boolean> map = ImmutableMap.of(
      "io.github.crazysmc.thrkbs.version.mixin.GameModeSwitcherScreenMixinOld", MC_VERSION.compareTo(V116PRE1) < 0,
      "io.github.crazysmc.thrkbs.version.mixin.GameModeSwitcherScreenMixinNew", MC_VERSION.compareTo(V116PRE1) >= 0
  );

  @Override
  public boolean shouldApplyMixin(String targetClassName, String mixinClassName)
  {
    return map.getOrDefault(mixinClassName, true);
  }
}
