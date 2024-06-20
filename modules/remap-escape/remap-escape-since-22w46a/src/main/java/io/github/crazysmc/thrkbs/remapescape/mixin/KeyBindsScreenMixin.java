package io.github.crazysmc.thrkbs.remapescape.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(KeyBindsScreen.class)
public abstract class KeyBindsScreenMixin
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = GLFW.GLFW_KEY_ESCAPE))
  private int remapEscapeKeyConstant(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }

  @Inject(method = "keyPressed", at = @At(value = "RETURN", ordinal = 1))
  private void remapEscapeKeySuper(CallbackInfoReturnable<Boolean> cir,
                                   @Local(ordinal = 0, argsOnly = true) LocalIntRef keyCode)
  {
    if (keyCode.get() == MAPPING_REGISTRY.remapKeyCode(GLFW.GLFW_KEY_ESCAPE))
      keyCode.set(GLFW.GLFW_KEY_ESCAPE);
  }
}
