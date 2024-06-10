package io.github.crazysmc.thrkbs.core;

import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import it.unimi.dsi.fastutil.chars.Char2CharArrayMap;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import org.lwjgl.glfw.GLFW;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicTextReplacer
{
  private final MappingRegistry mappingRegistry;
  private final KeyDisplay keyDisplay;
  private final Pattern F3_PLUS = Pattern.compile("\\bF3 \\+ (?:[A-ZΒ]|Esc|F4)\\b");
  private final Char2CharMap CHAR_MAP;

  public DynamicTextReplacer(MappingRegistry mappingRegistry, KeyDisplay keyDisplay)
  {
    this.mappingRegistry = mappingRegistry;
    this.keyDisplay = keyDisplay;
    char[] keys = { '4', 'c', /* Beta */ 'Β' };
    char[] values = { GLFW.GLFW_KEY_F4, GLFW.GLFW_KEY_ESCAPE, 'B' };
    CHAR_MAP = new Char2CharArrayMap(keys, values);
  }

  public String replaceF3Combos(String string)
  {
    Matcher matcher = F3_PLUS.matcher(string);
    if (matcher.find())
    {
      int start = matcher.start();
      int end = matcher.end();
      char original = string.charAt(end - 1);
      original = CHAR_MAP.getOrDefault(original, original);
      String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_F3));
      String key = keyDisplay.getDisplayName(mappingRegistry.getMapping(original));
      String replace = String.format("%s + %s", f3, key);
      String text = string.substring(0, start) + replace + string.substring(end);
      string = text.replace(string.substring(start, end), replace); // duplicate "F3 + C"
    }
    return string;
  }
}
