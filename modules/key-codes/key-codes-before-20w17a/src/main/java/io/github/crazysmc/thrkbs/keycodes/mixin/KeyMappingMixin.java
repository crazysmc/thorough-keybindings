package io.github.crazysmc.thrkbs.keycodes.mixin;

import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.crazysmc.thrkbs.core.api.KeyCodes.DEBUG_CATEGORY;

@Mixin(KeyBinding.class)
public abstract class KeyMappingMixin
{
  @Shadow
  @Final
  private String category;

  @Inject(method = "same", at = @At("HEAD"), cancellable = true)
  private void separateDebugCombos(KeyBinding keyMapping, CallbackInfoReturnable<Boolean> cir)
  {
    if (DEBUG_CATEGORY.equals(category) != DEBUG_CATEGORY.equals(keyMapping.getCategory()))
      cir.setReturnValue(false);
  }
}
