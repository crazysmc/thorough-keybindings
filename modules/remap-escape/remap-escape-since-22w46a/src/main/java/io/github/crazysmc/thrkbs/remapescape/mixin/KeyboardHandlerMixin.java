package io.github.crazysmc.thrkbs.remapescape.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @ModifyArg(method = "lambda$keyPress$4",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;keyPressed(III)Z"),
             index = 0)
  private static int remapEscapeKey(int keyCode, @Local(argsOnly = true) Screen instance)
  {
    return keyCode != MAPPING_REGISTRY.remapKeyCode(GLFW_KEY_ESCAPE) ||
        instance instanceof KeyBindsScreen ? keyCode : GLFW_KEY_ESCAPE;
  }
}
