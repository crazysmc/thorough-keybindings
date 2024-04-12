package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyMapping;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
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
}
