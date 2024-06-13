package io.github.crazysmc.thrkbs.module.mixin;

import io.github.crazysmc.thrkbs.core.MappingRegistry;
import io.github.crazysmc.thrkbs.core.ThoroughKeybindings;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ControlsScreen.class)
public abstract class ControlsScreenMixin
{
  @Unique
  private final MappingRegistry mappingRegistry = ThoroughKeybindings.getMappingRegistry();

  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = GLFW.GLFW_KEY_ESCAPE))
  private int remapKeyConstant(int constant)
  {
    return mappingRegistry.remapKeyCode(GLFW.GLFW_KEY_ESCAPE);
  }

  @ModifyArg(method = "keyPressed",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/OptionsSubScreen;keyPressed(III)Z"),
             index = 0)
  private int remapKeySuper(int key)
  {
    return key == mappingRegistry.remapKeyCode(GLFW.GLFW_KEY_ESCAPE) ? GLFW.GLFW_KEY_ESCAPE : key;
  }
}
