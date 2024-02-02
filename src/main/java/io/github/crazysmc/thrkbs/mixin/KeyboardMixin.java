package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.KeyMapping;
import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Keyboard.class, remap = false)
public abstract class KeyboardMixin
{
  @Inject(method = "getEventKey", at = @At("RETURN"), cancellable = true)
  private static void remapEventKey(CallbackInfoReturnable<Integer> cir)
  {
    KeyBinding binding = (KeyBinding) KeyBinding.BY_KEY_CODE.get(cir.getReturnValueI());
    KeyMapping mapping = ThoroughKeybindings.getMapping(binding);
    if (mapping != null)
      cir.setReturnValue(mapping.getOriginal());
  }
}
