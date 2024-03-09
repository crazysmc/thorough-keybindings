package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor
{
  @Accessor("ALL")
  //$if <1.12
  static java.util.List<KeyBinding> getAll()
  //$if >=1.12
  //$  static java.util.Map<String, KeyBinding> getAll()
  //$if
  {
    throw new AssertionError();
  }

  //$if >=1.13
  @Accessor("key")
  com.mojang.blaze3d.platform.InputConstants.Key getKey();
  //$if
}
