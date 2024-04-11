package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InputUtil.class)
public abstract class InputUtilMixin
{
  @ModifyVariable(method = "isKeyPressed", at = @At("LOAD"), argsOnly = true)
  private static int remapKeyDownArgument(int key)
  {
    return CustomKeyBinding.getKeyCodeByOriginal(key);
  }
}
