package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ControlsOptionsScreen.class)
public class ControlsOptionsScreenMixin extends Screen
{
  @ModifyArg(method = "init",
             at = @At(value = "INVOKE",
                      target = "Lnet/minecraft/client/gui/widget/OptionButtonWidget;<init>(IIIIILjava/lang/String;)V"),
             index = 2)
  private int init(int y)
  {
    if (y >= height / 6 + 168)
      y += 24;
    return y;
  }

  @ModifyArg(method = "render",
             at = @At(value = "INVOKE",
                      target = "Lnet/minecraft/client/gui/screen/options/ControlsOptionsScreen;drawString(Lnet/minecraft/client/render/TextRenderer;Ljava/lang/String;III)V"),
             index = 3)
  private int render(int y)
  {
    if (y >= height / 6 + 168 + 7)
      y += 24;
    return y;
  }
}
