package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Screen.class)
public class ScreenMixin extends GuiElement
{
  @ModifyConstant(method = "handleKeyboard", constant = @Constant(intValue = Keyboard.KEY_F11))
  private int handleKeyboard(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }
}
