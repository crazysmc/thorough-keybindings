package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.HardcodedMapping;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.crazysmc.thrkbs.InitVersion.KEYBOARD_HANDLER_MAPPINGS;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @ModifyVariable(method = "keyPress", at = @At("HEAD"), argsOnly = true, ordinal = 0)
  private int keyPress(int i, long _l, int _i, int j)
  {
    for (HardcodedMapping mapping : KEYBOARD_HANDLER_MAPPINGS)
      if (((KeyMapping) mapping.object).matches(i, j))
        return mapping.getKeyCode();
    return i;
  }
}
