package io.github.crazysmc.thrkbs.mixin.remap;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.CustomKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InputConstants.class)
public abstract class InputConstantsMixin
{
  @ModifyVariable(method = "isKeyDown", at = @At("LOAD"), argsOnly = true)
  private static int remapKeyDownArgument(int key)
  {
    return CustomKeyMapping.getKeyCodeByOriginal(key);
  }
}
