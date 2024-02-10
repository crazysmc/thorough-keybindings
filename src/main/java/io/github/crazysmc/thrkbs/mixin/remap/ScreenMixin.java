package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Screen.class)
public abstract class ScreenMixin
{
  @ModifyConstant(method = "isControlDown", constant = {
      @Constant(intValue = Keyboard.KEY_LCONTROL), @Constant(intValue = Keyboard.KEY_RCONTROL)
  })
  private static int remapControlKeys(int constant)
  {
    return CategorizedKeyBinding.getByOriginal(constant).keyCode;
  }

  @ModifyConstant(method = "isShiftDown", constant = {
      @Constant(intValue = Keyboard.KEY_LSHIFT), @Constant(intValue = Keyboard.KEY_RSHIFT)
  })
  private static int remapShiftKeys(int constant)
  {
    return CategorizedKeyBinding.getByOriginal(constant).keyCode;
  }

  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = Keyboard.KEY_ESCAPE))
  private int remapEscapeKey(int constant)
  {
    return CategorizedKeyBinding.getByOriginal(constant).keyCode;
  }

  @ModifyConstant(method = "handleKeyboard", constant = @Constant(intValue = Keyboard.KEY_F11))
  private int remapFullscreenKey(int constant)
  {
    return CategorizedKeyBinding.getByOriginal(constant).keyCode;
  }
}
