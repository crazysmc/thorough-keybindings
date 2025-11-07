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
        arguments("Tab + R = Reload chunks", "F3 + A = Reload chunks", "Tab", "r"),
        arguments("Tab + C = Copy location as /tp command, hold Tab + C to crash the game",
                  "F3 + C = Copy location as /tp command, hold F3 + C to crash the game", "Tab", "c"),
        arguments("Tab + Caps Lock = Pause without pause menu (if pausing is possible)",
                  "F3 + Esc = Pause without pause menu (if pausing is possible)", "Tab", "Caps Lock"),
        arguments("sʞunɥɔ pɐoꞁǝᴚ = Ɐ + ƐℲ", "sʞunɥɔ pɐoꞁǝᴚ = Ɐ + ƐℲ", "ƐℲ", "a"),
        arguments("snɔoɟ ʇsoꞁ uo ǝsnɐԀ = Ԁ + ƐℲ", "snɔoɟ ʇsoꞁ uo ǝsnɐԀ = Ԁ + Ɛ", "ƐℲ", "p"), /* we fix that missing Ⅎ */
        arguments("ɹǝɥɔʇᴉʍs ǝpoɯ ǝɯɐᵷ uǝdO = Z + qɐ⟘", "ɹǝɥɔʇᴉʍs ǝpoɯ ǝɯɐᵷ uǝdO = ߈Ⅎ + ƐℲ", "qɐ⟘", "z"),
        arguments("Tab + Firm. Maiusc. = Consiste sine aperire indicem optionum consistendi (si consistere licet)",
                  "F III + Fuga = Consiste sine aperire indicem optionum consistendi (si consistere licet)",
                  "Tab", "Firm. Maiusc."),
        arguments("Tab + Z = Aperi indicem modum mutandi", "F III + F IV = Aperi indicem modum mutandi", "Tab", "z"),
        arguments("Tab + E = Muta optiones emendandi", "F III + F VI = Muta optiones emendandi", "Tab", "e"),
        arguments("【Tab並A】復載區塊", "【F3並A】復載區塊", "Tab", "a"),
        arguments("【Tab + F】迂繞於異焉之繪距（撃Shift鍵以返迂焉）",
                  "【F3 + F】迂繞於異焉之繪距（撃Shift鍵以返迂焉）", "Tab", "f"), /* plus symbol in this one */
        arguments("F3並C既押。手不釋鍵則戲將崩於頃刻。", "F3並C既押。手不釋鍵則戲將崩於頃刻。", "F3", "c"),
        arguments("Tab + Caps Lock = Stop without stop list (if stopping is mightly)",
                  "F3 + Atwind = Stop without stop list (if stopping is mightly)", "Tab", "Caps Lock"),
        arguments("Tab + Caps Lock = Ehanañ hep al lañser (ma c'haller ehanañ)",
                  "F3 + Achap = Ehanañ hep al lañser (ma c'haller ehanañ)", "Tab", "Caps Lock"),
        arguments("Tab + Caps Lock = Mettre en pause sans afficher le menu (si le jeu le permet)",
                  "F3 + Échap = Mettre en pause sans afficher le menu (si le jeu le permet)", "Tab", "Caps Lock"),
        arguments("", "", "", "")
    );
  }

  public static Stream<Arguments> debugCharts()
  {
    return Stream.of(
        arguments("Debug charts: [Tab+A] Profiler hidden; [Tab+B] FPS + TPS hidden; [Tab+C] Ping hidden",
                  "Debug charts: [F3+1] Profiler hidden; [F3+2] FPS + TPS hidden; [F3+3] Ping hidden",
                  "Tab", "a", "b", "c"),
        arguments("", "", "", "", "", "")
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
