package io.github.crazysmc.thrkbs.keycodes.mixin;

import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface KeyMappingAccessor
{
  @Accessor("keyCode")
  int getKeyCode();
}
