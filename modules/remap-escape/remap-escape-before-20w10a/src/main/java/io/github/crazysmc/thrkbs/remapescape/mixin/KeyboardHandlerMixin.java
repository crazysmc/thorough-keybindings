package io.github.crazysmc.thrkbs.remapescape.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.unmapped.C_5751893;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @ModifyArg(method = "m_8874045",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/unmapped/C_5751893;keyPressed(III)Z"),
             index = 0)
  private int remapEscapeKey(int keyCode, @Local(argsOnly = true) C_5751893 instance)
  {
    return keyCode != MAPPING_REGISTRY.remapKeyCode(GLFW.GLFW_KEY_ESCAPE) ||
        instance instanceof ControlsOptionsScreen ? keyCode : GLFW.GLFW_KEY_ESCAPE;
  }
}
