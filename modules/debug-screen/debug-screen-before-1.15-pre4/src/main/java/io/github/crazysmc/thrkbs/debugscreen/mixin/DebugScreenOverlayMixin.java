package io.github.crazysmc.thrkbs.debugscreen.mixin;

import net.minecraft.client.gui.overlay.DebugOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.DYNAMIC_TEXT_REPLACER;

@Mixin(DebugOverlay.class)
public abstract class DebugScreenOverlayMixin
{
  @ModifyConstant(method = "drawGameInfo", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String replaceDebugHelpKeyText(String constant)
  {
    return DYNAMIC_TEXT_REPLACER.debugHelpKey(constant);
  }
}
