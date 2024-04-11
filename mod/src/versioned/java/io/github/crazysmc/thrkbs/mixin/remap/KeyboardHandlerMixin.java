//$if >=1.13.0
package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyBinding;
import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.chat.ChatGui;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @Unique
  private static final int[] DEBUG_KEYS = new int[] {
      GLFW.GLFW_KEY_A,
      GLFW.GLFW_KEY_B,
      GLFW.GLFW_KEY_C,
      GLFW.GLFW_KEY_D,
      GLFW.GLFW_KEY_F,
      GLFW.GLFW_KEY_G,
      GLFW.GLFW_KEY_H,
      GLFW.GLFW_KEY_I,
      GLFW.GLFW_KEY_N,
      GLFW.GLFW_KEY_P,
      GLFW.GLFW_KEY_Q,
      GLFW.GLFW_KEY_T,
  };

  @Unique
  private static final Pattern F3_PLUS = Pattern.compile("\\bF3 \\+ [A-ZΒ]\\b");

  @ModifyVariable(method = "handleDebugKeys", at = @At("LOAD"), argsOnly = true)
  private int remapDebugKeySwitch(int key)
  {
    for (int debugKey : DEBUG_KEYS)
      if (key == CustomKeyBinding.getKeyCodeByOriginal(debugKey))
        return debugKey;
    return -1;
  }

  /* lambda in method keyPress as argument to Screen.wrapScreenError */
  @ModifyArg(method = "m_8874045",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiEventListener;keyPressed(III)Z"),
             index = 0)
  private int remapKeyEscape(int key)
  {
    boolean gameMenu = key == CustomKeyBinding.getKeyCodeByOriginal(GLFW.GLFW_KEY_ESCAPE);
    return gameMenu ? GLFW.GLFW_KEY_ESCAPE : key;
  }

  @ModifyIntIfEqual(method = "keyPress",
                    slice = @Slice(from = @At(value = "INVOKE",
                                              target = "Lcom/mojang/blaze3d/platform/InputConstants;getKey(II)Lcom/mojang/blaze3d/platform/InputConstants$Key;")),
                    constant = @Constant)
  private int remapKeyConstant(int constant)
  {
    return CustomKeyBinding.getKeyCodeByOriginal(constant);
  }

  @Redirect(method = "handleDebugKeys",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/chat/ChatGui;addMessage(Lnet/minecraft/text/Text;)V"))
  private void debugHelpText(ChatGui instance, Text message)
  {
    String formatted = message.getFormattedString();
    Matcher matcher = F3_PLUS.matcher(formatted);
    if (matcher.find())
    {
      int end = matcher.end();
      char original = formatted.charAt(end - 1);
      StringBuilder sb = new StringBuilder(formatted.length() + 16);
      sb.append(formatted, 0, matcher.start());
      sb.append(CustomKeyBinding.getDisplayNameByOriginal(GLFW.GLFW_KEY_F3));
      sb.append(" + ");
      sb.append(CustomKeyBinding.getDisplayNameByOriginal(original == 'Β' ? 'B' : original)); // replace Beta
      sb.append(formatted, end, formatted.length());
      message = new LiteralText(sb.toString());
    }
    instance.addMessage(message);
  }
}
