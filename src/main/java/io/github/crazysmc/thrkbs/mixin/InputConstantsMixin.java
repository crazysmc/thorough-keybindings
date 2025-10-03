package io.github.crazysmc.thrkbs.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;

@Mixin(InputConstants.class)
public abstract class InputConstantsMixin
{
  @ModifyVariable(method = "isKeyDown", at = @At("LOAD"), argsOnly = true)
  private static int remapKeyDownArgument(int key)
  {
//    int code = MAPPING_REGISTRY.remapKeyCode(key); TODO
//    return code == GLFW_KEY_UNKNOWN ? key : code;
    return key;
  }
}
