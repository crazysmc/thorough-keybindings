package io.github.crazysmc.thrkbs.core.mixin.remap;

import io.github.crazysmc.thrkbs.core.ThoroughKeybindings;
import io.github.crazysmc.thrkbs.core.MappingRegistry;
import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin
{
  @Unique
  private static final MappingRegistry mappingRegistry = ThoroughKeybindings.getMappingRegistry();
  @Unique
  private static final KeyDisplay keyDisplay = KeyDisplay.getProvider();
  @Unique
  private static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");

  @ModifyConstant(method = "drawGameInformation", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String debugHelpText(String constant)
  {
    String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_F3));
    String q = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_Q));
    return String.format("For help: press %s + %s", f3, q);
  }

  @ModifyArg(method = "drawGameInformation",
             at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
  private Object debugChartsText(Object object)
  {
    String string = (String) object;
    if (!string.startsWith("Debug charts: "))
      return object;
    String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_F3));
    String key1 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_1));
    String key2 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_2));
    String key3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_3));
    return string //
        .replace("[F3+1]", String.format("[%s+%s]", f3, key1))
        .replace("[F3+2]", String.format("[%s+%s]", f3, key2))
        .replace("[F3+3]", String.format("[%s+%s]", f3, key3));
  }
}
