package io.github.crazysmc.thrkbs.mixin.remap;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Screen.class)
public abstract class ScreenMixin
{
  @ModifyExpressionValue(method = "handleKeyboard",
                         at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", remap = false))
  private int remapEventKey(int eventKey)
  {
    return CategorizedKeyBinding.getOriginalByKeyCode(eventKey);
  }

  @ModifyArg(method = "*",
             at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z", remap = false))
  private static int remapKeyDownArgument(int key)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(key);
  }
}
