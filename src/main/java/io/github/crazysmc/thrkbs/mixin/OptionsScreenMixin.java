package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.CategoryOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen
{
  @Shadow
  @Final
  private GameOptions options;

  @ModifyArg(method = "buttonClicked",
             slice = @Slice(from = @At(value = "NEW",
                                       target = "(Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/client/options/GameOptions;)Lnet/minecraft/client/gui/screen/options/ControlsOptionsScreen;")),
             at = @At(value = "INVOKE",
                      target = "Lnet/minecraft/client/Minecraft;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V",
                      ordinal = 0))
  private Screen redirectControlsOptions(Screen screen)
  {
    return screen instanceof ControlsOptionsScreen ? new CategoryOptionsScreen(this, options) : screen;
  }
}
