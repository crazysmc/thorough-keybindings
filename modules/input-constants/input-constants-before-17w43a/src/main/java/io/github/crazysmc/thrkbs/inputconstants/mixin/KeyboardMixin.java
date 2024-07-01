package io.github.crazysmc.thrkbs.inputconstants.mixin;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;
import static org.lwjgl.input.Keyboard.KEYBOARD_SIZE;

@Mixin(value = Keyboard.class, remap = false)
public abstract class KeyboardMixin
{
  @ModifyVariable(method = "isKeyDown", at = @At("LOAD"), argsOnly = true)
  private static int remapKeyDownArgument(int key)
  {
    int code = MAPPING_REGISTRY.remapKeyCode(key);
    return code < KEYBOARD_SIZE ? code : key;
  }
}
