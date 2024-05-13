package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyMapping;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

//$if <1.18
@Mixin(net.minecraft.client.gui.screens.controls.ControlsScreen.class)
//$if >=1.18
@Mixin(net.minecraft.client.gui.screens.controls.KeyBindsScreen.class)
//$if
public abstract class KeyBindsScreenMixin
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = GLFW.GLFW_KEY_ESCAPE))
  private int remapKeyConstant(int constant)
  {
    return CustomKeyMapping.getKeyCodeByOriginal(constant);
  }

  @ModifyVariable(method = "keyPressed", at = @At(value = "LOAD", ordinal = 2), ordinal = 0, argsOnly = true)
  private int remapKeySuper(int key)
  {
    return key == CustomKeyMapping.getKeyCodeByOriginal(GLFW.GLFW_KEY_ESCAPE) ? GLFW.GLFW_KEY_ESCAPE : key;
  }
}
