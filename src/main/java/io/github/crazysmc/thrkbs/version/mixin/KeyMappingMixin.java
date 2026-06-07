package io.github.crazysmc.thrkbs.version.mixin;

import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin
{
  @Inject(method = "same", at = @At("HEAD"), cancellable = true)
  private void same(KeyMapping keyMapping, CallbackInfoReturnable<Boolean> cir)
  {
    if ((Object) this instanceof KeyRemapping != keyMapping instanceof KeyRemapping)
      cir.setReturnValue(false);
  }
}
