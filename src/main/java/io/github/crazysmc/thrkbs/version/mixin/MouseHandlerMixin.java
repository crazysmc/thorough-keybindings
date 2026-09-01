package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin
{
  @WrapOperation(
      method = "onButton",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/gui/screens/Screen;mouseClicked(Lnet/minecraft/client/input/MouseButtonEvent;Z)Z"
      )
  )
  private boolean onButton_mouseClicked(Screen screen, MouseButtonEvent event,
                                        boolean doubleClick, Operation<Boolean> original)
  {
    boolean closesScreen = event.isEscape() && screen.shouldCloseOnEsc();
    if (screen instanceof KeyBindsScreen)
      return original.call(screen, event, doubleClick);
    else if (!closesScreen)
      return original.call(screen, event, doubleClick);

    screen.onClose();
    return true;
  }

  @WrapOperation(
      method = "onButton",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/KeyMapping;click(Lcom/mojang/blaze3d/platform/InputConstants$Key;)V"
      )
  )
  private void onButton_click(InputConstants.Key key, Operation<Void> original,
                              long window, MouseButtonInfo buttonInfo, int action)
  {
    MouseButtonEvent event = new MouseButtonEvent(0, 0, buttonInfo);
    if (event.isEscape())
    {
      Minecraft.getInstance().pauseGame(false);
      return;
    }
    original.call(key);
  }
}
