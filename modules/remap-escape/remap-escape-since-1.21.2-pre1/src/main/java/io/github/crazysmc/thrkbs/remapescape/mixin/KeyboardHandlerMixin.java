package io.github.crazysmc.thrkbs.remapescape.mixin;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @Shadow
  @Final
  private Minecraft minecraft;

  @ModifyArg(method = "keyPress",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;keyPressed(III)Z"),
             index = 0)
  private int remapEscapeKey(int keyCode)
  {
    return keyCode != MAPPING_REGISTRY.remapKeyCode(GLFW_KEY_ESCAPE) ||
        minecraft.screen instanceof KeyBindsScreen ? keyCode : GLFW_KEY_ESCAPE;
  }
}
