package io.github.crazysmc.thrkbs.core.mixin;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.crazysmc.thrkbs.core.HardcodedMapping.DEBUG_CATEGORY;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin
{
  @Shadow
  @Final
  private String category;

  @Inject(method = "same", at = @At("HEAD"), cancellable = true)
  private void separateDebugCombos(KeyMapping keyMapping, CallbackInfoReturnable<Boolean> cir)
  {
    if (DEBUG_CATEGORY.equals(category) != DEBUG_CATEGORY.equals(keyMapping.getCategory()))
      cir.setReturnValue(false);
  }
}
