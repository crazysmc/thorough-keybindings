package io.github.crazysmc.thrkbs;

import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2CharOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicTextReplacer
{
  private static final Logger LOGGER = LogManager.getLogger();
  private static final Pattern F3_PLUS_KEY = Pattern.compile("F(?:3| III) \\+ (?:Esc|F4|[A-ZΒ])");
  private static final Pattern F3_PLUS_KEY_CN = Pattern.compile("【F3並[^】]+】");
  private static final Pattern F3_PLUS_KEY_UD = Pattern.compile("[^ ]+ \\+ ƐℲ?");
  private static final Char2CharMap CHAR_MAP_UD = new Char2CharOpenHashMap(new char[] {
      '!', '"', '&', '\'', '(', ')', ',', '.', '1', '2', '3', '4', '5', '6', '7', '9', ';', '<', '>', '?', 'A', 'B',
      'C', 'D', 'E', 'F', 'G', 'J', 'K', 'L', 'M', 'P', 'Q', 'R', 'T', 'U', 'V', 'W', 'Y', '[', ']', '_', 'a', 'b', 'c',
      'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 't', 'u', 'v', 'w', 'y', '{', '}',
  }, new char[] {
      '¡', '„', '⅋', ',', ')', '(', '‘', '˙', '⥝', 'ᘔ', 'Ɛ', '߈', 'ϛ', '9', 'ㄥ', '6', '⸵', '>', '<', '¿', 'Ɐ', 'ᗺ',
      'Ɔ', 'ᗡ', 'Ǝ', 'Ⅎ', '⅁', 'Ր', 'Ʞ', 'Ꞁ', 'W', 'Ԁ', 'Ꝺ', 'ᴚ', '⟘', '∩', 'Ʌ', 'M', '⅄', ']', '[', '‾', 'ɐ', 'q', 'ɔ',
      'p', 'ǝ', 'ɟ', 'ᵷ', 'ɥ', 'ᴉ', 'ɾ', 'ʞ', 'ꞁ', 'ɯ', 'u', 'd', 'b', 'ɹ', 'ʇ', 'n', 'ʌ', 'ʍ', 'ʎ', '}', '{',
  });

  public static String debugHelpMessage(String text, String f3, String key)
  {
    boolean f3Letter = f3.length() == 1;
    if (f3Letter)
      f3 = f3.toUpperCase(Locale.ROOT);
    boolean keyLetter = key.length() == 1;
    if (keyLetter)
      key = key.toUpperCase(Locale.ROOT);
    Matcher matcher = F3_PLUS_KEY.matcher(text);
    if (matcher.find())
      return matcher.replaceAll(String.format("%s + %s", f3, key));
    Matcher matcherUd = F3_PLUS_KEY_UD.matcher(text);
    if (matcherUd.find())
    {
      if (f3Letter)
      {
        char f3Char = f3.charAt(0);
        f3 = String.valueOf(CHAR_MAP_UD.getOrDefault(f3Char, f3Char));
      }
      if (keyLetter)
      {
        char keyChar = key.charAt(0);
        key = String.valueOf(CHAR_MAP_UD.getOrDefault(keyChar, keyChar));
      }
      return matcherUd.replaceAll(String.format("%s + %s", key, f3));
    }
    Matcher matcherCn = F3_PLUS_KEY_CN.matcher(text);
    if (matcherCn.find())
      return matcherCn.replaceAll(String.format("【%s並%s】", f3, key));
    LOGGER.warn("could not find F3 key combination in '{}'", text);
    return text;
  }

  private static String upsideDown(String string)
  {
    int length = string.length();
    StringBuilder sb = new StringBuilder(length);
    for (int i = length - 1; i >= 0; i--)
    {
      char c = string.charAt(i);
      sb.append(CHAR_MAP_UD.getOrDefault(c, c));
    }
    LOGGER.debug("translated '{}' to '{}'", string, sb.toString());
    return sb.toString();
  }
}
