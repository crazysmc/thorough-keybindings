package io.github.crazysmc.thrkbs.core;

import io.github.crazysmc.thrkbs.core.api.KeyDisplay;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.lwjgl.glfw.GLFW.*;

public class DynamicTextReplacer
{
  private final MappingRegistry mappingRegistry;
  private final KeyDisplay keyDisplay;
  private final Pattern f3PlusKey = Pattern.compile("\\bF3 \\+ (?:[A-ZΒ]|Esc|F4)\\b");

  public DynamicTextReplacer(MappingRegistry mappingRegistry, KeyDisplay keyDisplay)
  {
    this.mappingRegistry = mappingRegistry;
    this.keyDisplay = keyDisplay;
  }

  public String debugHelpKey(String string)
  {
    String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW_KEY_F3));
    String q = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW_KEY_Q));
    return f3 == null || q == null ? string : String.format("For help: press %s + %s", f3, q);
  }

  public String debugChartsKeys(String string)
  {
    if (!string.startsWith("Debug charts: "))
      return string;
    String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW_KEY_F3));
    if (f3 == null)
      return string;
    String key1 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW_KEY_1));
    if (key1 != null)
      string = string.replace("[F3+1]", String.format("[%s+%s]", f3, key1));
    String key2 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW_KEY_2));
    if (key2 != null)
      string = string.replace("[F3+2]", String.format("[%s+%s]", f3, key2));
    String key3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW_KEY_3));
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
      String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(GLFW_KEY_F3));
      String key = keyDisplay.getDisplayName(mappingRegistry.getMapping(getKeyCode(original)));
      if (f3 == null || key == null)
        return string;
      String replace = String.format("%s + %s", f3, key);
      string = string.substring(0, start) +
          replace +
          string.substring(end).replace(matcher.group(), replace); // duplicate "F3 + C"
    }
    return string;
  }

  private int getKeyCode(char original)
  {
    switch (original)
    {
      case '4': // F4
        return GLFW_KEY_F4;
      case 'c': // Esc
        return GLFW_KEY_ESCAPE;
      case 'Β': // Beta
        return 'B';
      default:
        return original;
    }
  }
}
