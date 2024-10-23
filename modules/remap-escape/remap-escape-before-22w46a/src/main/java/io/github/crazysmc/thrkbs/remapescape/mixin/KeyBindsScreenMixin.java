package io.github.crazysmc.thrkbs.remapescape.mixin;

import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static io.github.crazysmc.thrkbs.core.MappingRegistry.MAPPING_REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

@Mixin(KeyBindsScreen.class)
public abstract class KeyBindsScreenMixin
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = GLFW_KEY_ESCAPE))
  private int remapEscapeKeyConstant(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }

  @ModifyArg(method = "keyPressed",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/OptionsSubScreen;keyPressed(III)Z"),
             index = 0)
  private int remapEscapeKeySuper(int keyCode)
  {
    return keyCode == MAPPING_REGISTRY.remapKeyCode(GLFW_KEY_ESCAPE) ? GLFW_KEY_ESCAPE : keyCode;
  }
}
