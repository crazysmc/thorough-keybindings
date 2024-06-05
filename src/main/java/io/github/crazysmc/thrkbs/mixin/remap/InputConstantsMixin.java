package io.github.crazysmc.thrkbs.mixin.remap;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.MappingRegistry;
import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InputConstants.class)
public abstract class InputConstantsMixin
{
  @Unique
  private static final MappingRegistry mappingRegistry = ThoroughKeybindings.getMappingRegistry();

  @ModifyVariable(method = "isKeyDown", at = @At("LOAD"), argsOnly = true)
  private static int remapKeyDownArgument(int key)
  {
    return mappingRegistry.remapKeyCode(key);
  }
}
