package io.github.crazysmc.thrkbs;

import java.util.Arrays;
import java.util.stream.Stream;

import static io.github.crazysmc.thrkbs.MappingCategory.*;
import static io.github.crazysmc.thrkbs.ThoroughKeybindings.*;
import static io.github.crazysmc.thrkbs.Versions.*;
import static org.lwjgl.glfw.GLFW.*;

public enum HardcodedMapping
{
  GAME_MENU(MISC, GLFW_KEY_ESCAPE, "gameMenu", "pause", true),
  TOGGLE_GUI(MISC, GLFW_KEY_F1, "toggleGui", NO_DEBUG_BINDS),
  DEBUG_INFO(MISC, GLFW_KEY_F3, "debug.modifier", NO_DEBUG_BINDS),
  TOGGLE_SHADER(MISC, GLFW_KEY_F4, "toggleSpectatorShaderEffects", MC_VERSION.compareTo(V20W20A) < 0),
  GAME_MODE(MISC, GLFW_KEY_F4, "switchGameMode", "gamemodes", MC_VERSION.compareTo(V20W20A) >= 0 && NO_DEBUG_BINDS),
  DEBUG_OPTIONS(MISC, MC_VERSION.compareTo(V12109PRE4) < 0 ? GLFW_KEY_F5 : GLFW_KEY_F6, "debug.debugOptions", "options",
                MC_VERSION.compareTo(V25W31A) >= 0 && NO_DEBUG_BINDS),
  NARRATOR(MISC, GLFW_KEY_B, "narrator", !NO_DEBUG_BINDS),

  CHARTS_PROFILER(DEBUG, GLFW_KEY_1, "profilingChart", "charts.profiler",
                  MC_VERSION.compareTo(V23W33A) >= 0 && NO_DEBUG_BINDS),
  CHARTS_FPS(DEBUG, GLFW_KEY_2, "fpsCharts", "charts.fps",
             MC_VERSION.compareTo(V23W33A) >= 0 && NO_DEBUG_BINDS),
  CHARTS_NETWORK(DEBUG, GLFW_KEY_3, "networkCharts", "charts.network",
                 MC_VERSION.compareTo(V23W33A) >= 0 && NO_DEBUG_BINDS),

  RELOAD_CHUNKS(DEBUG, GLFW_KEY_A, "reloadChunk", "reload_chunks", NO_DEBUG_BINDS),
  SHOW_HITBOXES(DEBUG, GLFW_KEY_B, "showHitboxes", "show_hitboxes", NO_DEBUG_BINDS),
  COPY_LOCATION(DEBUG, GLFW_KEY_C, "copyLocation", "copy_location", NO_DEBUG_BINDS),
  CLEAR_CHAT(DEBUG, GLFW_KEY_D, "clearChat", "clear_chat", NO_DEBUG_BINDS),
  RENDER_DISTANCE(DEBUG, GLFW_KEY_F, "cycleRenderDistance", "cycle_renderdistance", MC_VERSION.compareTo(V22W12A) < 0),
  CHUNK_BOUNDARIES(DEBUG, GLFW_KEY_G, "showChunkBorders", "chunk_boundaries", NO_DEBUG_BINDS),
  ADVANCED_TOOLTIPS(DEBUG, GLFW_KEY_H, "showAdvancedTooltips", "advanced_tooltips", NO_DEBUG_BINDS),
  INSPECT(DEBUG, GLFW_KEY_I, "copyRecreateCommand", "inspect", NO_DEBUG_BINDS),
  PROFILING(DEBUG, GLFW_KEY_L, "profiling", "profiling", MC_VERSION.compareTo(V21W11A) >= 0 && NO_DEBUG_BINDS),
  SPECTATE(DEBUG, GLFW_KEY_N, "spectate", "creative_spectator", NO_DEBUG_BINDS),
  FOCUS_PAUSE(DEBUG, GLFW_KEY_P, "focusPause", "pause_focus", NO_DEBUG_BINDS),
  HELP(DEBUG, GLFW_KEY_Q, "help", "help", NO_DEBUG_BINDS),
  DUMP_DYN_TEX(DEBUG, GLFW_KEY_S, "dumpDynamicTextures", "dump_dynamic_textures",
               MC_VERSION.compareTo(V11904PRE3) >= 0 && NO_DEBUG_BINDS),
  RELOAD_RESOURCE_PACKS(DEBUG, GLFW_KEY_T, "reloadResourcePacks", "reload_resourcepacks", NO_DEBUG_BINDS),
  DUMP_VERSION(DEBUG, GLFW_KEY_V, "dumpVersion", "version", MC_VERSION.compareTo(V25W15A) >= 0 && NO_DEBUG_BINDS),

  SHIFT_1(MODIFIER, GLFW_KEY_LEFT_SHIFT, "mod.shift.1"),
  SHIFT_2(MODIFIER, GLFW_KEY_RIGHT_SHIFT, "mod.shift.2"),
  CTRL_1(MODIFIER, ON_OSX && NO_DEBUG_BINDS ? GLFW_KEY_LEFT_SUPER : GLFW_KEY_LEFT_CONTROL, "mod.ctrl.1"),
  CTRL_2(MODIFIER, ON_OSX && NO_DEBUG_BINDS ? GLFW_KEY_RIGHT_SUPER : GLFW_KEY_RIGHT_CONTROL, "mod.ctrl.2"),
  ALT_1(MODIFIER, GLFW_KEY_LEFT_ALT, "mod.alt.1"),
  ALT_2(MODIFIER, GLFW_KEY_RIGHT_ALT, "mod.alt.2"),

  PROFILER_0(PROFILER, GLFW_KEY_0, "profiler.up"),
  PROFILER_1(PROFILER, GLFW_KEY_1, "profiler.1"),
  PROFILER_2(PROFILER, GLFW_KEY_2, "profiler.2"),
  PROFILER_3(PROFILER, GLFW_KEY_3, "profiler.3"),
  PROFILER_4(PROFILER, GLFW_KEY_4, "profiler.4"),
  PROFILER_5(PROFILER, GLFW_KEY_5, "profiler.5"),
  PROFILER_6(PROFILER, GLFW_KEY_6, "profiler.6"),
  PROFILER_7(PROFILER, GLFW_KEY_7, "profiler.7"),
  PROFILER_8(PROFILER, GLFW_KEY_8, "profiler.8"),
  PROFILER_9(PROFILER, GLFW_KEY_9, "profiler.9"),
  ;

  private final MappingCategory category;
  private final int keyCode;
  private final String name;
  private final String debugHelp;
  private final boolean exists;

  HardcodedMapping(MappingCategory category, int keyCode, String name)
  {
    this(category, keyCode, name, null, true);
  }

  HardcodedMapping(MappingCategory category, int keyCode, String name, boolean exists)
  {
    this(category, keyCode, name, null, exists);
  }

  HardcodedMapping(MappingCategory category, int keyCode, String name, String debugHelp, boolean exists)
  {
    this.category = category;
    this.keyCode = keyCode;
    this.name = category == DEBUG ? String.format("debug.%s", name) : name;
    this.debugHelp = debugHelp;
    this.exists = exists;
  }

  public static Stream<HardcodedMapping> getExisting()
  {
    return Arrays.stream(values()).filter(mapping -> mapping.exists);
  }

  public MappingCategory getCategory()
  {
    return category;
  }

  public int getKeyCode()
  {
    return keyCode;
  }

  public String getId()
  {
    return String.format("key.%s", name);
  }

  public String getDebugHelpId()
  {
    return debugHelp == null ? null : String.format("debug.%s.help", debugHelp);
  }
}
