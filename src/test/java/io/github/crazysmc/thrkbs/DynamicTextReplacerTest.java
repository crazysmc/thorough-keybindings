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

  public static Stream<Arguments> debugCharts()
  {
    return Stream.of(
        arguments("", "", "", "", "", ""),
        arguments("Debug charts: [Tab+A] Profiler hidden; [Tab+B] FPS + TPS hidden; [Tab+C] Ping hidden",
                  "Debug charts: [F3+1] Profiler hidden; [F3+2] FPS + TPS hidden; [F3+3] Ping hidden",
                  "Tab", "a", "b", "c")
    );
  }

  @ParameterizedTest
  @MethodSource
  void debugHelpMessage(String expected, String text, String f3, String key)
  {
    assertEquals(expected, DynamicTextReplacer.debugHelpMessage(text, f3, key));
  }

  @ParameterizedTest
  @MethodSource
  void debugCharts(String expected, String text, String f3, String chart1, String chart2, String chart3)
  {
    assertEquals(expected, DynamicTextReplacer.debugCharts(text, f3, chart1, chart2, chart3));
  }
}
