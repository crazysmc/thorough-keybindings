package io.github.crazysmc.thrkbs.module.mixin;

import net.minecraft.client.gui.screens.controls.ControlsScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(ControlsScreen.class)
public abstract class ControlsScreenMixin
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = GLFW.GLFW_KEY_ESCAPE))
  private int remapKeyConstant(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(GLFW.GLFW_KEY_ESCAPE);
  }

  @ModifyArg(method = "keyPressed",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;keyPressed(III)Z"),
             index = 0)
  private int remapKeySuper(int key)
  {
    return key == MAPPING_REGISTRY.remapKeyCode(GLFW.GLFW_KEY_ESCAPE) ? GLFW.GLFW_KEY_ESCAPE : key;
  }
}
