package io.github.crazysmc.thrkbs.remapescape.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @ModifyArg(method = "lambda$keyPress$4",
             at = @At(value = "INVOKE",
                      target = "Lnet/minecraft/client/gui/components/events/ContainerEventHandler;keyPressed(III)Z"),
             index = 0)
  private int remapEscapeKey(int keyCode, @Local(argsOnly = true) ContainerEventHandler instance)
  {
    return keyCode != MAPPING_REGISTRY.remapKeyCode(GLFW_KEY_ESCAPE) ||
        instance instanceof ControlsScreen ? keyCode : GLFW_KEY_ESCAPE;
  }
}
