package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyMapping;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin
{
  @ModifyConstant(method = "drawGameInformation", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String debugHelpText(String constant)
  {
    String f3 = CustomKeyMapping.getDisplayNameByOriginal(GLFW.GLFW_KEY_F3);
    String q = CustomKeyMapping.getDisplayNameByOriginal(GLFW.GLFW_KEY_Q);
    return String.format("For help: press %s + %s", f3, q);
  }

  @ModifyArg(method = "drawGameInformation",
             at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
  private Object debugChartsText(Object object)
  {
    String string = (String) object;
    if (!string.startsWith("Debug charts: "))
      return object;
    String f3 = CustomKeyMapping.getDisplayNameByOriginal(GLFW.GLFW_KEY_F3);
    String key1 = CustomKeyMapping.getDisplayNameByOriginal(GLFW.GLFW_KEY_1);
    String key2 = CustomKeyMapping.getDisplayNameByOriginal(GLFW.GLFW_KEY_2);
    String key3 = CustomKeyMapping.getDisplayNameByOriginal(GLFW.GLFW_KEY_3);
    return string //
        .replace("[F3+1]", String.format("[%s+%s]", f3, key1))
        .replace("[F3+2]", String.format("[%s+%s]", f3, key2))
        .replace("[F3+3]", String.format("[%s+%s]", f3, key3));
  }
}
