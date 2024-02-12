package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin
{
  @Inject(method = "resetMapping", at = @At("HEAD"))
  private static void resetMapping(CallbackInfo ci)
  {
    CategorizedKeyBinding.resetMapping();
  }
}
