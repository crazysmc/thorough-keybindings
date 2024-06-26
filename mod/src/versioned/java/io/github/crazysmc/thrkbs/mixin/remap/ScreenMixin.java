package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyBinding;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Screen.class)
public abstract class ScreenMixin
{
  //$if <1.7.4
  @ModifyConstant(method = "handleKeyboard", constant = @Constant(intValue = org.lwjgl.input.Keyboard.KEY_F11))
  private int remapKeyConstant(int constant)
  {
    return CustomKeyBinding.getKeyCodeByOriginal(constant);
  }

  //$if <1.13.0
  @ModifyArg(method = "handleKeyboard",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;keyPressed(CI)V"))
  private int remapEscape(int key)
  {
    boolean gameMenu = key == CustomKeyBinding.getKeyCodeByOriginal(org.lwjgl.input.Keyboard.KEY_ESCAPE);
    return gameMenu ? org.lwjgl.input.Keyboard.KEY_ESCAPE : key;
  }
  //$if
}
