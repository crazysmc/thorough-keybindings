package io.github.crazysmc.thrkbs.module.mixin;

import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.crazysmc.thrkbs.core.HardcodedMapping.DEBUG_KEYS;
import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerCommonMixin
{
  @ModifyVariable(method = "handleDebugKeys", at = @At("LOAD"), argsOnly = true)
  private int remapDebugKeySwitch(int key)
  {
    for (int debugKey : DEBUG_KEYS)
      if (key == MAPPING_REGISTRY.remapKeyCode(debugKey))
        return debugKey;
    return -1;
  }

  @ModifyIntIfEqual(method = "keyPress", constant = @Constant)
  private int remapKeyConstant(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }
}
