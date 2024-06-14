package io.github.crazysmc.thrkbs.module.mixin;

import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.KEY_DISPLAY;
import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin
{
  @ModifyConstant(method = "drawGameInformation", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String debugHelpText(String constant)
  {
    String f3 = KEY_DISPLAY.getDisplayName(MAPPING_REGISTRY.getMapping(GLFW.GLFW_KEY_F3));
    String q = KEY_DISPLAY.getDisplayName(MAPPING_REGISTRY.getMapping(GLFW.GLFW_KEY_Q));
    return f3 == null || q == null ? constant : String.format("For help: press %s + %s", f3, q);
  }

  @ModifyArg(method = "drawGameInformation",
             at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
  private Object debugChartsText(Object object)
  {
    String string = (String) object;
    if (!string.startsWith("Debug charts: "))
      return object;
    String f3 = KEY_DISPLAY.getDisplayName(MAPPING_REGISTRY.getMapping(GLFW.GLFW_KEY_F3));
    if (f3 == null)
      return object;
    String key1 = KEY_DISPLAY.getDisplayName(MAPPING_REGISTRY.getMapping(GLFW.GLFW_KEY_1));
    if (key1 != null)
      string = string.replace("[F3+1]", String.format("[%s+%s]", f3, key1));
    String key2 = KEY_DISPLAY.getDisplayName(MAPPING_REGISTRY.getMapping(GLFW.GLFW_KEY_2));
    if (key2 != null)
      string = string.replace("[F3+2]", String.format("[%s+%s]", f3, key2));
    String key3 = KEY_DISPLAY.getDisplayName(MAPPING_REGISTRY.getMapping(GLFW.GLFW_KEY_3));
    if (key3 != null)
      string = string.replace("[F3+3]", String.format("[%s+%s]", f3, key3));
    return string;
  }
}
