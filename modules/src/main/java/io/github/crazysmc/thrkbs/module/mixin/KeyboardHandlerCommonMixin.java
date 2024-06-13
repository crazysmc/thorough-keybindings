package io.github.crazysmc.thrkbs.module.mixin;

import io.github.crazysmc.thrkbs.core.HardcodedMapping;
import io.github.crazysmc.thrkbs.core.MappingRegistry;
import io.github.crazysmc.thrkbs.core.ThoroughKeybindings;
import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerCommonMixin
{
  @Unique
  private final MappingRegistry mappingRegistry = ThoroughKeybindings.getMappingRegistry();

  @Unique
  private final int[] debugKeys = HardcodedMapping.getDebugKeys()
      .stream()
      .mapToInt(HardcodedMapping::getKeyCode)
      .toArray();

  @ModifyVariable(method = "handleDebugKeys", at = @At("LOAD"), argsOnly = true)
  private int remapDebugKeySwitch(int key)
  {
    for (int debugKey : debugKeys)
      if (key == mappingRegistry.remapKeyCode(debugKey))
        return debugKey;
    return -1;
  }

//  @Inject(method = "keyPress", at = @At("HEAD"))
//  private void x(long l, int i, int j, int k, int m, CallbackInfo ci)
//  {
//  }

  @ModifyIntIfEqual(method = "keyPress", constant = @Constant)
  private int remapKeyConstant(int constant)
  {
    return mappingRegistry.remapKeyCode(constant);
  }
}
