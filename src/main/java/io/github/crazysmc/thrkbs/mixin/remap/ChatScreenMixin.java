package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = Keyboard.KEY_ESCAPE, ordinal = 0))
  private int remapEscapeKey(int constant)
  {
    return CategorizedKeyBinding.getByOriginal(constant).keyCode;
  }
}
