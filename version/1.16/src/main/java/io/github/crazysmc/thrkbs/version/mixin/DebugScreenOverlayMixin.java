package io.github.crazysmc.thrkbs.version.mixin;

import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Optional;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.debugHelpMessage;
import static io.github.crazysmc.thrkbs.HardcodedMapping.DEBUG_INFO;
import static io.github.crazysmc.thrkbs.HardcodedMapping.HELP;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin
{
  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @ModifyConstant(method = "drawGameInformation", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String drawGameInformation(String constant)
  {
    String f3 = REGISTRY.get(DEBUG_INFO).getTranslatedKeyMessage().visitSelf(Optional::of).get();
    String key = REGISTRY.get(HELP).getTranslatedKeyMessage().visitSelf(Optional::of).get();
    return debugHelpMessage(constant, f3, key);
  }
}
