package io.github.crazysmc.thrkbs.debugscreen.mixin;

import net.minecraft.client.gui.overlay.DebugOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
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

  @ModifyArg(method = "drawGameInfo", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
  private Object debugChartsText(Object object)
  {
    return DYNAMIC_TEXT_REPLACER.debugChartsKeys((String) object);
  }
}
