package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyMapping;
import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2CharOpenHashMap;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
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
      GLFW.GLFW_KEY_1,
      GLFW.GLFW_KEY_2,
      GLFW.GLFW_KEY_3,
      GLFW.GLFW_KEY_A,
      GLFW.GLFW_KEY_B,
      GLFW.GLFW_KEY_C,
      GLFW.GLFW_KEY_D,
      GLFW.GLFW_KEY_F,
      GLFW.GLFW_KEY_G,
      GLFW.GLFW_KEY_H,
      GLFW.GLFW_KEY_I,
      GLFW.GLFW_KEY_L,
      GLFW.GLFW_KEY_N,
      GLFW.GLFW_KEY_P,
      GLFW.GLFW_KEY_Q,
      GLFW.GLFW_KEY_S,
      GLFW.GLFW_KEY_T,
      GLFW.GLFW_KEY_F4,
  };

  @Unique
  private static final Pattern F3_PLUS = Pattern.compile("\\bF3 \\+ (?:[A-ZΒ]|Esc|F4)\\b");
  @Unique
  private static final Char2CharMap CHAR_MAP = new Char2CharOpenHashMap(new char[] { '4', 'c', /* Beta */ 'Β' },
                                                                        new char[] {
                                                                            GLFW.GLFW_KEY_F4, GLFW.GLFW_KEY_ESCAPE, 'B'
                                                                        });

  /* lambda in method keyPress as argument to Screen.wrapScreenError */
  @ModifyArg(method = "method_1454", at = @At(value = "INVOKE",
                                              //$if <1.17
                                              target = "Lnet/minecraft/client/gui/components/events/ContainerEventHandler;keyPressed(III)Z"
                                              //$if >=1.17
                                              target= "Lnet/minecraft/client/gui/screens/Screen;keyPressed(III)Z"
                                              //$if
                                              ), index = 0)
  private
  //$if >=1.19.3
  static
  //$if
  int remapKeyEscape(int key)
  {
    boolean gameMenu = key == CustomKeyMapping.getKeyCodeByOriginal(GLFW.GLFW_KEY_ESCAPE);
    return gameMenu ? GLFW.GLFW_KEY_ESCAPE : key;
  }

  @ModifyVariable(method = "handleDebugKeys", at = @At("LOAD"), argsOnly = true)
  private int remapDebugKeySwitch(int key)
  {
    for (int debugKey : DEBUG_KEYS)
      if (key == CustomKeyMapping.getKeyCodeByOriginal(debugKey))
        return debugKey;
    return -1;
  }

  @ModifyIntIfEqual(method = "keyPress", constant = @Constant)
  private int remapKeyConstant(int constant)
  {
    return CustomKeyMapping.getKeyCodeByOriginal(constant);
  }

  @Redirect(method = "handleDebugKeys",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;)V"))
  private void debugHelpText(ChatComponent instance, Component component)
  {
    String formatted = component.getString();
    Matcher matcher = F3_PLUS.matcher(formatted);
    if (matcher.find())
    {
      int end = matcher.end();
      char original = formatted.charAt(end - 1);
      original = CHAR_MAP.getOrDefault(original, original);
      StringBuilder sb = new StringBuilder(formatted.length() + 16);
      sb.append(formatted, 0, matcher.start());
      sb.append(CustomKeyMapping.getDisplayNameByOriginal(GLFW.GLFW_KEY_F3));
      sb.append(" + ");
      sb.append(CustomKeyMapping.getDisplayNameByOriginal(original));
      sb.append(formatted, end, formatted.length());
      //$if <1.19
      component = new net.minecraft.network.chat.TextComponent(sb.toString());
      //$if >=1.19
      component = Component.literal(sb.toString());
      //$if
    }
    instance.addMessage(component);
  }
}
