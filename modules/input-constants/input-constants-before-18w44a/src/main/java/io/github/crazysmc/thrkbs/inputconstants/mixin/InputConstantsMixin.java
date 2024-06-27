package io.github.crazysmc.thrkbs.inputconstants.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(InputConstants.class)
public abstract class InputConstantsMixin
{
  @ModifyVariable(method = "getKey(I)Z", at = @At("LOAD"), argsOnly = true)
  private static int remapKeyDownArgument(int key)
  {
    return MAPPING_REGISTRY.remapKeyCode(key);
  }
}
