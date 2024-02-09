package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.gui.screen.CreditsScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CreditsScreen.class)
public abstract class CreditsScreenMixin
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = Keyboard.KEY_ESCAPE))
  private int remapEscapeKey(int constant)
  {
    return CategorizedKeyBinding.getByOriginal(constant).keyCode;
  }
}
