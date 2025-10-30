package io.github.crazysmc.thrkbs.version.mixin;

import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.debugHelpMessage;
import static io.github.crazysmc.thrkbs.HardcodedMapping.DEBUG_INFO;
import static io.github.crazysmc.thrkbs.HardcodedMapping.HELP;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin
{
  @ModifyConstant(method = "drawGameInformation", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String drawGameInformation(String constant)
  {
    String f3 = KeyRemapping.get(DEBUG_INFO).getTranslatedKeyMessage();
    String key = KeyRemapping.get(HELP).getTranslatedKeyMessage();
    return debugHelpMessage(constant, f3, key);
  }
}
