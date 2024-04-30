package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor
{
  //$if >=1.0.0-beta.1.8.0.z <1.12.0
  @Accessor("ALL")
  static List<KeyBinding> getAllList()
  {
    throw new AssertionError();
  }

  //$if >=1.12.0
  @Accessor("ALL")
  static Map<String, KeyBinding> getAllMap()
  {
    throw new AssertionError();
  }

  //$if <1.13.0
  @Accessor("keyCode")
  int getKeyCode();

  //$if >=1.13.0
  @Accessor("key")
  com.mojang.blaze3d.platform.InputConstants.Key getKey();
  //$if
}
