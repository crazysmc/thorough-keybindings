package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.ControlsOptionsListScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.options.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen
{
  @Shadow
  @Final
  private GameOptions options;

  @ModifyArg(method = "buttonClicked",
             at = @At(value = "INVOKE",
                      target = "Lnet/minecraft/client/Minecraft;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V",
                      ordinal = 1))
  private Screen buttonClicked(Screen screen)
  {
    return new ControlsOptionsListScreen(this, options);
  }
}
