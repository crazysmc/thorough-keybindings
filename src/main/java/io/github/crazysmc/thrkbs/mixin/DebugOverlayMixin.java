package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.overlay.DebugOverlay;
import net.minecraft.client.options.GameOptions;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(DebugOverlay.class)
public abstract class DebugOverlayMixin extends GuiElement
{
  @ModifyConstant(method = "drawGameInfo", constant = @Constant(stringValue = "For help: press F3 + Q"))
  private String drawGameInfoString(String constant)
  {
    return String.format("For help: press %s + %s",
                         GameOptions.getKeyName(ThoroughKeybindings.getRemap(Keyboard.KEY_F3)),
                         GameOptions.getKeyName(ThoroughKeybindings.getRemap(Keyboard.KEY_Q)));
  }
}
