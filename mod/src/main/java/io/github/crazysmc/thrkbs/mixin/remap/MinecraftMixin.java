package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.ChatGui;
import net.minecraft.client.options.GameOptions;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @Unique
  private static final Pattern F3_PLUS = Pattern.compile("\\bF3 \\+ [A-ZΒ]\\b");

  @Unique
  private int index;

  /* before 1.9 */
  @Group
  @ModifyIntIfEqual(method = "tick",
                    slice = @Slice(from = @At(value = "INVOKE:LAST",
                                              target = "Lnet/minecraft/client/options/KeyBinding;click(I)V"),
                                   to = @At(value = "INVOKE:FIRST",
                                            target = "Lnet/minecraft/client/options/KeyBinding;consumeClick()Z")),
                    constant = @Constant)
  private int remapKeyConstantTick(int constant)
  {
    if (constant != Keyboard.KEY_1)
      return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
    int i = index;
    index = i == 8 ? 0 : i + 1;
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant + i) - i;
  }

  /* since 1.9 */
  @Group
  @ModifyIntIfEqual(method = { "handleKeyboardEvents", "handleDebugKey" }, constant = @Constant)
  private int remapKeyConstantHandle(int constant)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
  }

  /* since 1.9, replace Beta with B, does not work for Upside Down lang */
  @Redirect(method = "handleDebugKey",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/chat/ChatGui;addMessage(Lnet/minecraft/text/Text;)V"),
            require = 0)
  private void debugHelpText(ChatGui instance, Text message)
  {
    String formatted = message.getFormattedString();
    Matcher matcher = F3_PLUS.matcher(formatted);
    if (matcher.find())
    {
      int end = matcher.end();
      char original = formatted.charAt(end - 1);
      int keyIndex = Keyboard.getKeyIndex(String.valueOf(original == 'Β' ? 'B' : original));
      StringBuilder sb = new StringBuilder(formatted.length() + 16);
      sb.append(formatted, 0, matcher.start());
      sb.append(GameOptions.getKeyName(CategorizedKeyBinding.getKeyCodeByOriginal(Keyboard.KEY_F3)));
      sb.append(" + ");
      sb.append(GameOptions.getKeyName(CategorizedKeyBinding.getKeyCodeByOriginal(keyIndex)));
      sb.append(formatted, end, formatted.length());
      message = new LiteralText(sb.toString());
    }
    instance.addMessage(message);
  }
}
