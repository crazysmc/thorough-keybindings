package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.gui.overlay.DebugOverlay;
import net.minecraft.client.options.GameOptions;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DebugOverlay.class)
public abstract class DebugOverlayMixin
{
  @ModifyConstant(method = "drawGameInfo", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String debugHelpText(String constant)
  {
    String f3 = GameOptions.getKeyName(CategorizedKeyBinding.getKeyCodeByOriginal(Keyboard.KEY_F3));
    String q = GameOptions.getKeyName(CategorizedKeyBinding.getKeyCodeByOriginal(Keyboard.KEY_Q));
    return String.format("For help: press %s + %s", f3, q);
  }
}
