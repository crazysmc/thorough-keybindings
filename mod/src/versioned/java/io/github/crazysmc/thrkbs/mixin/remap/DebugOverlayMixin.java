//$if >=1.9.0
package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyBinding;
import net.minecraft.client.gui.overlay.DebugOverlay;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DebugOverlay.class)
public abstract class DebugOverlayMixin
{
  @ModifyConstant(method = "drawGameInfo", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String debugHelpText(String constant)
  {
    //$if >=1.9.0 <1.13.0
    String f3 = GameOptions.getKeyName(CustomKeyBinding.getKeyCodeByOriginal(org.lwjgl.input.Keyboard.KEY_F3));
    String q = GameOptions.getKeyName(CustomKeyBinding.getKeyCodeByOriginal(org.lwjgl.input.Keyboard.KEY_Q));
    //$if >=1.13.0
    String f3 = CustomKeyBinding.getDisplayNameByOriginal(org.lwjgl.glfw.GLFW.GLFW_KEY_F3);
    String q = CustomKeyBinding.getDisplayNameByOriginal(org.lwjgl.glfw.GLFW.GLFW_KEY_Q);
    //$if >=1.9.0
    return String.format("For help: press %s + %s", f3, q);
  }
}
