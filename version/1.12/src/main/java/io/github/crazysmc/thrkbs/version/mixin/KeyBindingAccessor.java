package io.github.crazysmc.thrkbs.version.mixin;

import net.minecraft.client.options.KeyBinding;
import net.minecraft.util.Int2ObjectHashMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor
{
  /*
   * type as of KeyBindingMixin
   */
  @Accessor("BY_KEY_CODE")
  static Int2ObjectHashMap<List<KeyBinding>> getMap()
  {
    throw new AssertionError();
  }
}
