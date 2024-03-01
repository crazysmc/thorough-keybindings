package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor
{
  @Accessor("ALL")
  static Map<String, KeyBinding> getAll()
  {
    throw new AssertionError();
  }
}
