package io.github.crazysmc.thrkbs.remapescape.mixin;

import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(ControlsOptionsScreen.class)
public abstract class ControlsScreenMixin
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = Keyboard.KEY_ESCAPE))
  private int remapEscapeKeyConstant(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }

  @ModifyArg(method = "keyPressed",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;keyPressed(CI)V"))
  private int remapEscapeKeySuper(char chr, int key)
  {
    return key == MAPPING_REGISTRY.remapKeyCode(Keyboard.KEY_ESCAPE) ? Keyboard.KEY_ESCAPE : key;
  }
}
