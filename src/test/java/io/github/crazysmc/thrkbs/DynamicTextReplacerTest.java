package io.github.crazysmc.thrkbs;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DynamicTextReplacerTest
{
  public static Stream<Arguments> debugHelpMessage()
  {
    return Stream.of(
        arguments("", "", "", ""),
        arguments("Tab + R = Reload chunks", "F3 + A = Reload chunks", "Tab", "r"),
        arguments("Tab + C = Copy location as /tp command, hold Tab + C to crash the game",
                     "F3 + C = Copy location as /tp command, hold F3 + C to crash the game", "Tab", "c"),
        arguments("Tab + Caps Lock = Pause without pause menu (if pausing is possible)",
                     "F3 + Esc = Pause without pause menu (if pausing is possible)", "Tab", "Caps Lock"),
        arguments("sʞunɥɔ pɐoꞁǝᴚ = Ɐ + ƐℲ", "sʞunɥɔ pɐoꞁǝᴚ = Ɐ + ƐℲ", "ƐℲ", "a")
    );
  }

  @ParameterizedTest
  @MethodSource
  void debugHelpMessage(String expected, String text, String f3, String key)
  {
    assertEquals(expected, DynamicTextReplacer.debugHelpMessage(text, f3, key));
  }
}
