package io.github.crazysmc.thrkbs.mixin.screen;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = Keyboard.KEY_ESCAPE, ordinal = 0))
  private int keyPressedRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }
}
