package io.github.crazysmc.thrkbs.core;

import io.github.crazysmc.thrkbs.core.api.KeyCodes;
import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import io.github.crazysmc.thrkbs.core.api.MappingRegistry;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicTextReplacer
{
  private final MappingRegistry mappingRegistry;
  private final KeyDisplay keyDisplay;
  private final KeyCodes keyCodes;
  private final Pattern f3PlusKey = Pattern.compile("\\bF3 \\+ (?:[A-ZΒ]|Esc|F4)\\b");

  public DynamicTextReplacer(MappingRegistry mappingRegistry, KeyDisplay keyDisplay, KeyCodes keyCodes)
  {
    this.mappingRegistry = mappingRegistry;
    this.keyDisplay = keyDisplay;
    this.keyCodes = keyCodes;
  }

  public String debugHelpKey(String string)
  {
    String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(keyCodes.getF3KeyCode()));
    String q = keyDisplay.getDisplayName(mappingRegistry.getMapping(keyCodes.getLetterKeyCode('Q')));
    return f3 == null || q == null ? string : String.format("For help: press %s + %s", f3, q);
  }

  public String debugHelpList(String string)
  {
    Matcher matcher = f3PlusKey.matcher(string);
    if (matcher.find())
    {
      int start = matcher.start();
      int end = matcher.end();
      char original = string.charAt(end - 1);
      String f3 = keyDisplay.getDisplayName(mappingRegistry.getMapping(keyCodes.getF3KeyCode()));
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
        return keyCodes.getF4KeyCode();
      case 'c': // Esc
        return keyCodes.getEscKeyCode();
      case 'Β': // Beta
        return keyCodes.getLetterKeyCode('B');
      default:
        return keyCodes.getLetterKeyCode(original);
    }
  }
}
