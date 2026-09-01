package io.github.crazysmc.thrkbs.version.mixin;

import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.input.MouseButtonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBindsScreen.class)
public abstract class KeyBindsScreenMixin
{
  @Inject(
      method = "mouseClicked",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/gui/screens/options/OptionsSubScreen;mouseClicked(Lnet/minecraft/client/input/MouseButtonEvent;Z)Z"
      ),
      cancellable = true
  )
  private void mouseClicked(MouseButtonEvent event, boolean doubleClick,
                            CallbackInfoReturnable<Boolean> cir)
  {
    KeyBindsScreen screen = (KeyBindsScreen) (Object) this;
    if (event.isEscape() && screen.shouldCloseOnEsc())
    {
      screen.onClose();
      cir.setReturnValue(true);
    }
  }
}
