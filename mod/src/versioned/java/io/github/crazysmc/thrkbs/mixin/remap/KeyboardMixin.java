//$if <1.13.0
package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyBinding;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = Keyboard.class, remap = false)
public class KeyboardMixin
{
  @ModifyVariable(method = "isKeyDown", at = @At("LOAD"), argsOnly = true)
  private static int remapKeyDownArgument(int key)
  {
    int code = CustomKeyBinding.getKeyCodeByOriginal(key);
    return code < 256 ? code : key;
  }
}
