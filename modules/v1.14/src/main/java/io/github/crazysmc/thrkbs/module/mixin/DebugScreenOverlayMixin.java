package io.github.crazysmc.thrkbs.module.mixin;

import io.github.crazysmc.thrkbs.core.MappingRegistry;
import io.github.crazysmc.thrkbs.core.ThoroughKeybindings;
import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin
{
  @Unique
  private static final MappingRegistry mappingRegistry = ThoroughKeybindings.getMappingRegistry();
  @Unique
  private static final KeyDisplay keyDisplay = KeyDisplay.getProvider();

  @ModifyConstant(method = "drawGameInformation", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String debugHelpText(String constant)
  {
    String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_F3));
    String q = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_Q));
    return f3 == null || q == null ? constant : String.format("For help: press %s + %s", f3, q);
  }
}
