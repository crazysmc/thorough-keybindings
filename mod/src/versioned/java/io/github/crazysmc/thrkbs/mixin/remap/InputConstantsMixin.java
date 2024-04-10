//$if >=1.13.0
package io.github.crazysmc.thrkbs.mixin.remap;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.CustomKeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InputConstants.class)
public abstract class InputConstantsMixin
{
  @ModifyVariable(method = "getKey(I)Z", at = @At("LOAD"), argsOnly = true)
  private static int remapKeyDownArgument(int key)
  {
    return CustomKeyBinding.getKeyCodeByOriginal(key);
  }
}
