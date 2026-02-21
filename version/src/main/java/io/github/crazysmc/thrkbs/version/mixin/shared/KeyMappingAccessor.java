package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(KeyMapping.class)
public interface KeyMappingAccessor
{
  /*
   * type since 25w36a, use ThoroughKeybindings.MULTIMAP before that
   */
  @Accessor("MAP")
  static Map<InputConstants.Key, List<KeyMapping>> getMap()
  {
    throw new AssertionError();
  }
}
