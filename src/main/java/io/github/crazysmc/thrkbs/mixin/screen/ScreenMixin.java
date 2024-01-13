package io.github.crazysmc.thrkbs.mixin.screen;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Screen.class)
public abstract class ScreenMixin extends GuiElement
{
  @ModifyConstant(method = "isControlDown", constant = {
      @Constant(intValue = Keyboard.KEY_LCONTROL), @Constant(intValue = Keyboard.KEY_RCONTROL),
  })
  private static int isControlDownRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }

  @ModifyConstant(method = "isShiftDown", constant = {
      @Constant(intValue = Keyboard.KEY_LSHIFT), @Constant(intValue = Keyboard.KEY_RSHIFT),
  })
  private static int isShiftDownRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }

  @ModifyConstant(method = "handleKeyboard", constant = @Constant(intValue = Keyboard.KEY_F11))
  private int handleKeyboardRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }

  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = Keyboard.KEY_ESCAPE))
  private int keyPressedRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }
}
