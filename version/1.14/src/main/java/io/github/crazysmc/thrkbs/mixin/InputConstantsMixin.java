package io.github.crazysmc.thrkbs.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.HardcodedMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.crazysmc.thrkbs.InitVersion.getMapping;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;

@Mixin(InputConstants.class)
public abstract class InputConstantsMixin
{
  @Unique
  private static final Logger LOGGER = LogManager.getLogger();

  @ModifyVariable(method = "isKeyDown", at = @At("LOAD"), argsOnly = true)
  private static int remapKeyDownArgument(int key)
  {
    HardcodedMapping mapping = getMapping(key);
    if (mapping == null)
    {
      LOGGER.warn("missing mapping for key {}", key);
      return key;
    }
    int code = ((KeyMappingAccessor) mapping.object).getKey().getValue();
    return code == GLFW_KEY_UNKNOWN ? key : code;
  }
}
