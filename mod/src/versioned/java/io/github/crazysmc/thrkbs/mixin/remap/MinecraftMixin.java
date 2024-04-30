//$if <1.13.0
package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyBinding;
import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  //$if >=1.9.0 <1.13.0
  @Unique
  private static final Pattern F3_PLUS = Pattern.compile("\\bF3 \\+ [A-ZΒ]\\b");
  //$if <1.13.0
  @Unique
  private int index;

  @ModifyIntIfEqual(method = "tick",
                    //$if <1.0.0-beta.1.8.0.z
                    slice = @Slice(from = @At(value = "INVOKE:LAST",
                                              target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z",
                                              remap = false),
                                   to = @At(value = "INVOKE:FIRST",
                                            target = "Lorg/lwjgl/input/Mouse;isButtonDown(I)Z",
                                            remap = false)),
                    //$if >=1.0.0-beta.1.8.0.z <1.3.0
                    slice = @Slice(from = @At(value = "INVOKE:LAST",
                                              target = "Lnet/minecraft/client/options/KeyBinding;onKeyPressed(I)V"),
                                   to = @At(value = "INVOKE:FIRST",
                                            target = "Lnet/minecraft/client/options/KeyBinding;wasPressed()Z")),
                    //$if >=1.3.0
                    slice = @Slice(from = @At(value = "INVOKE:LAST",
                                              target = "Lnet/minecraft/client/options/KeyBinding;click(I)V"),
                                   to = @At(value = "INVOKE:FIRST",
                                            target = "Lnet/minecraft/client/options/KeyBinding;consumeClick()Z")),
                    //$if <1.13.0
                    constant = @Constant)
  private int remapKeyConstantTick(int constant)
  {
    if (constant != Keyboard.KEY_1)
      return CustomKeyBinding.getKeyCodeByOriginal(constant);
    int i = index;
    index = i == 8 ? 0 : i + 1;
    return CustomKeyBinding.getKeyCodeByOriginal(constant + i) - i;
  }

  //$if >=1.9.0 <1.13.0
  @ModifyIntIfEqual(method = { "handleKeyboardEvents", "handleDebugKey" }, constant = @Constant)
  private int remapKeyConstantHandle(int constant)
  {
    return CustomKeyBinding.getKeyCodeByOriginal(constant);
  }

  @Redirect(method = "handleDebugKey",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/chat/ChatGui;addMessage(Lnet/minecraft/text/Text;)V"))
  private void debugHelpText(net.minecraft.client.gui.chat.ChatGui instance, net.minecraft.text.Text message)
  {
    String formatted = message.getFormattedString();
    Matcher matcher = F3_PLUS.matcher(formatted);
    if (matcher.find())
    {
      int end = matcher.end();
      char original = formatted.charAt(end - 1);
      int keyIndex = Keyboard.getKeyIndex(String.valueOf(original == 'Β' ? 'B' : original)); // replace Beta
      StringBuilder sb = new StringBuilder(formatted.length() + 16);
      sb.append(formatted, 0, matcher.start());
      sb.append(GameOptions.getKeyName(CustomKeyBinding.getKeyCodeByOriginal(Keyboard.KEY_F3)));
      sb.append(" + ");
      sb.append(GameOptions.getKeyName(CustomKeyBinding.getKeyCodeByOriginal(keyIndex)));
      sb.append(formatted, end, formatted.length());
      message = new net.minecraft.text.LiteralText(sb.toString());
    }
    instance.addMessage(message);
  }
  //$if <1.13.0
}
