package io.github.crazysmc.thrkbs.version.mixin;

import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(InputWithModifiers.class)
public interface InputWithModifiersMixin
{
  @Inject(method = "isEscape", at = @At("HEAD"), cancellable = true)
  default void isEscape(CallbackInfoReturnable<Boolean> cir)
  {
    InputWithModifiers instance = (InputWithModifiers) this;
    if (!(instance instanceof KeyEvent))
      return;
    KeyRemapping remapping = REGISTRY.get(GAME_MENU);
    if (!remapping.isUnbound())
      cir.setReturnValue(remapping.matches((KeyEvent) instance));
  }
}
