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
  private final Pattern f3PlusKey = Pattern.compile("\\bF3 \\+ (?:[A-ZΒ]|Esc|F4)\\b");
  private final Char2CharMap charMap;

  public DynamicTextReplacer(MappingRegistry mappingRegistry, KeyDisplay keyDisplay)
  {
    this.mappingRegistry = mappingRegistry;
    this.keyDisplay = keyDisplay;
    char[] keys = { '4', 'c', /* Beta */ 'Β' };
    char[] values = { GLFW.GLFW_KEY_F4, GLFW.GLFW_KEY_ESCAPE, 'B' };
    charMap = new Char2CharArrayMap(keys, values);
  }

  public String debugHelpKey(String string)
  {
    String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_F3));
    String q = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_Q));
    return f3 == null || q == null ? string : String.format("For help: press %s + %s", f3, q);
  }

  public String debugChartsKeys(String string)
  {
    if (!string.startsWith("Debug charts: "))
      return string;
    String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_F3));
    if (f3 == null)
      return string;
    String key1 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_1));
    if (key1 != null)
      string = string.replace("[F3+1]", String.format("[%s+%s]", f3, key1));
    String key2 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_2));
    if (key2 != null)
      string = string.replace("[F3+2]", String.format("[%s+%s]", f3, key2));
    String key3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_3));
    if (key3 != null)
      string = string.replace("[F3+3]", String.format("[%s+%s]", f3, key3));
    return string;
  }

  public String debugHelpList(String string)
  {
    Matcher matcher = f3PlusKey.matcher(string);
    if (matcher.find())
    {
      int start = matcher.start();
      int end = matcher.end();
      char original = string.charAt(end - 1);
      original = charMap.getOrDefault(original, original);
      String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW.GLFW_KEY_F3));
      String key = keyDisplay.getDisplayName(mappingRegistry.getMapping(original));
      String replace = String.format("%s + %s", f3, key);
      String text = string.substring(0, start) + replace + string.substring(end);
      string = text.replace(matcher.group(), replace); // duplicate "F3 + C"
    }
    return string;
  }
}
