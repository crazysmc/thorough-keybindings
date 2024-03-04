package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor
{
  @Accessor("ALL")
  //$if <1.12
  static List<KeyBinding> getAll()
  //$if >=1.12
  static Map<String, KeyBinding> getAll()
  //$if
  {
    throw new AssertionError();
  }
}
