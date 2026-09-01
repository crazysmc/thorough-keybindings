package io.github.crazysmc.thrkbs.version.mixin;

import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
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
    KeyRemapping remapping = REGISTRY.get(GAME_MENU);
    if (remapping.isUnbound())
      return;
    if (instance instanceof KeyEvent keyEvent)
      cir.setReturnValue(remapping.matches(keyEvent));
    else if (instance instanceof MouseButtonEvent mouseEvent)
      cir.setReturnValue(remapping.matchesMouse(mouseEvent));
  }
}
