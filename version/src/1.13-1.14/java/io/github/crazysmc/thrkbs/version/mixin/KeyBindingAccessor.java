package io.github.crazysmc.thrkbs.version.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor
{
  /*
   * type as of KeyBindingMixinGLFW
   */
  @Accessor("BY_KEY")
  static Map<InputConstants.Key, List<KeyBinding>> getMap()
  {
    throw new AssertionError();
  }
}
