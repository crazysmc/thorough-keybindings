package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.input.MouseButtonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.mojang.blaze3d.platform.InputConstants.UNKNOWN;

@Mixin(KeyBindsScreen.class)
public abstract class KeyBindsScreenMixin
{
  @ModifyArg(
      method = "mouseClicked",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/KeyMapping;setKey(Lcom/mojang/blaze3d/platform/InputConstants$Key;)V"
      )
  )
  private InputConstants.Key mouseClicked_setKey(InputConstants.Key key, @Local(argsOnly = true) MouseButtonEvent event)
  {
    return event.isEscape() ? UNKNOWN : key;
  }

  @Inject(
      method = "mouseClicked",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/gui/screens/options/OptionsSubScreen;mouseClicked" +
              "(Lnet/minecraft/client/input/MouseButtonEvent;Z)Z"
      ),
      cancellable = true
  )
  private void mouseClicked_super(MouseButtonEvent event, boolean doubleClick,
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
