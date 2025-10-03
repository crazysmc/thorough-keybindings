package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.crazysmc.thrkbs.MappingCategory.DEBUG;
import static io.github.crazysmc.thrkbs.MappingCategory.MODIFIER;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin
{
  @Shadow
  @Final
  private String category;

  @Inject(method = "same", at = @At("HEAD"), cancellable = true)
  private void separateDebugCombos(KeyMapping keyMapping, CallbackInfoReturnable<Boolean> cir)
  {
    if (DEBUG.equals(category) != DEBUG.equals(keyMapping.getCategory()) ||
        MODIFIER.equals(category) != MODIFIER.equals(keyMapping.getCategory()))
      cir.setReturnValue(false);
  }
}
