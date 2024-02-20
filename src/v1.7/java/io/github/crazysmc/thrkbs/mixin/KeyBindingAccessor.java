package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor
{
  @Accessor("ALL")
  static List<KeyBinding> getAll()
  {
    throw new AssertionError();
  }
}
