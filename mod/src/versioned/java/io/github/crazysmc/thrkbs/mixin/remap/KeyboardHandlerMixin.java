//$if >=1.13
package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
//  @ModifyConstant(method = "handleDebugKeys", constant = @Constant(intValue = 65))
//  private int remapCaseLabel(int constant)
//  {
//    return constant;
//  }

  @ModifyIntIfEqual(method = "keyPress", constant = @Constant)
  private int remapKeyConstant(int constant)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
  }
}
