package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.ChatGui;
import net.minecraft.client.options.GameOptions;
import net.minecraft.snooper.SnooperPopulator;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.BlockableEventLoop;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements BlockableEventLoop, SnooperPopulator
{
  @Shadow
  protected abstract void selectProfilerChartSection(int section);

  @ModifyConstant(method = "handleKeyboardEvents", constant = {
      @Constant(intValue = Keyboard.KEY_ESCAPE, ordinal = 0),
      @Constant(intValue = Keyboard.KEY_0),
      @Constant(intValue = Keyboard.KEY_F1),
      @Constant(intValue = Keyboard.KEY_F3),
      @Constant(intValue = Keyboard.KEY_F4),
      @Constant(intValue = Keyboard.KEY_C),
  })
  private int handleKeyboardEventsRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }

  @ModifyConstant(method = "handleDebugKey", constant = {
      @Constant(intValue = Keyboard.KEY_T),
      @Constant(intValue = Keyboard.KEY_A),
      @Constant(intValue = Keyboard.KEY_F),
      @Constant(intValue = Keyboard.KEY_P),
      @Constant(intValue = Keyboard.KEY_H),
      @Constant(intValue = Keyboard.KEY_B),
      @Constant(intValue = Keyboard.KEY_D),
      @Constant(intValue = Keyboard.KEY_Q),
      @Constant(intValue = Keyboard.KEY_N),
      @Constant(intValue = Keyboard.KEY_G),
  })
  private int handleDebugKeyRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }

  @Redirect(method = "handleDebugKey",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/chat/ChatGui;addMessage(Lnet/minecraft/text/Text;)V"))
  private void constructTranslatableText(ChatGui chatGui, Text message)
  {
    String string = message.getContent();
    if (!string.startsWith("F3 + "))
      return;
    String f3Key = GameOptions.getKeyName(ThoroughKeybindings.getRemap(Keyboard.KEY_F3));
    String comboKey = GameOptions.getKeyName(
        ThoroughKeybindings.getRemap(Keyboard.getKeyIndex(string.substring(5, 6))));
    chatGui.addMessage(new LiteralText(String.format("%s + %s%s", f3Key, comboKey, string.substring(6))));
  }

  @ModifyConstant(method = "handleKeyboardEvents", constant = @Constant(intValue = 9, ordinal = 0))
  private int handleKeyboardEventsProfilerChart(int constant)
  {
    for (int i = 0; i < 9; i++)
      if (Keyboard.getEventKey() == ThoroughKeybindings.getProfilerRemap(i))
        selectProfilerChartSection(i + 1);
    return 0;
  }
}
