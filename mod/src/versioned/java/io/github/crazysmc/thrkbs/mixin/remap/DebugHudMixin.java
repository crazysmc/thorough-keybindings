package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyBinding;
import net.minecraft.client.gui.hud.DebugHud;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin
{
  @ModifyConstant(method = "renderLeftText", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String debugHelpText(String constant)
  {
    String f3 = CustomKeyBinding.getDisplayNameByOriginal(GLFW.GLFW_KEY_F3);
    String q = CustomKeyBinding.getDisplayNameByOriginal(GLFW.GLFW_KEY_Q);
    return String.format("For help: press %s + %s", f3, q);
  }
}
