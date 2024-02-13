package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Screen.class)
public abstract class ScreenMixin
{
  @ModifyArg(method = "*",
             at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z", remap = false))
  private static int remapKeyDownArgument(int key)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(key);
  }

  @ModifyConstant(method = "handleKeyboard", constant = @Constant(intValue = Keyboard.KEY_F11))
  private int remapKeyConstant(int constant)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
  }

  @ModifyArg(method = "handleKeyboard",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;keyPressed(CI)V"))
  private int remapEscape(int key)
  {
    return key == CategorizedKeyBinding.getKeyCodeByOriginal(Keyboard.KEY_ESCAPE) ? Keyboard.KEY_ESCAPE : key;
  }
}
