package io.github.crazysmc.thrkbs;

import java.util.Arrays;
import java.util.stream.Stream;

import static io.github.crazysmc.thrkbs.MappingCategory.*;
import static io.github.crazysmc.thrkbs.ThoroughKeybindings.MC_VERSION;
import static io.github.crazysmc.thrkbs.ThoroughKeybindings.ON_OSX;
import static io.github.crazysmc.thrkbs.Versions.*;
import static org.lwjgl.glfw.GLFW.*;

public enum HardcodedMapping
{
  GAME_MENU(MISC, GLFW_KEY_ESCAPE, "gameMenu", "debug.pause"),
  TOGGLE_HUD(MISC, GLFW_KEY_F1, "toggleHUD"),
  DEBUG_INFO(MISC, GLFW_KEY_F3, "debugInfo"),
  DISABLE_SHADER(MISC, GLFW_KEY_F4, "disableShader", MC_VERSION.compareTo(V20W20A) < 0),
  GAME_MODE(MISC, GLFW_KEY_F4, "gameMode_disableShader", "debug.gamemodes", MC_VERSION.compareTo(V20W20A) >= 0),

  CHARTS_PROFILER(DEBUG, GLFW_KEY_1, "debug.charts.profiler", MC_VERSION.compareTo(V23W33A) >= 0),
  CHARTS_FPS(DEBUG, GLFW_KEY_2, "debug.charts.fps", MC_VERSION.compareTo(V23W33A) >= 0),
  CHARTS_NETWORK(DEBUG, GLFW_KEY_3, "debug.charts.network", MC_VERSION.compareTo(V23W33A) >= 0),
  RELOAD_CHUNKS(DEBUG, GLFW_KEY_A, "debug.reload_chunks"),
  SHOW_HITBOXES(DEBUG, GLFW_KEY_B, "debug.show_hitboxes"),
  COPY_LOCATION(DEBUG, GLFW_KEY_C, "debug.copy_location"),
  CLEAR_CHAT(DEBUG, GLFW_KEY_D, "debug.clear_chat"),
  CYCLE_RENDERDISTANCE(DEBUG, GLFW_KEY_F, "debug.cycle_renderdistance", MC_VERSION.compareTo(V22W12A) < 0),
  CHUNK_BOUNDARIES(DEBUG, GLFW_KEY_G, "debug.chunk_boundaries"),
  ADVANCED_TOOLTIPS(DEBUG, GLFW_KEY_H, "debug.advanced_tooltips"),
  INSPECT(DEBUG, GLFW_KEY_I, "debug.inspect"),
  PROFILING(DEBUG, GLFW_KEY_L, "debug.profiling", MC_VERSION.compareTo(V21W11A) >= 0),
  CREATIVE_SPECTATOR(DEBUG, GLFW_KEY_N, "debug.creative_spectator"),
  PAUSE_FOCUS(DEBUG, GLFW_KEY_P, "debug.pause_focus"),
  HELP(DEBUG, GLFW_KEY_Q, "debug.help"),
  DUMP_DYNAMIC_TEXTURES(DEBUG, GLFW_KEY_S, "debug.dump_dynamic_textures", MC_VERSION.compareTo(V11904PRE3) >= 0),
  RELOAD_RESOURCEPACKS(DEBUG, GLFW_KEY_T, "debug.reload_resourcepacks"),
  CLIENT_VERSION(DEBUG, GLFW_KEY_V, "debug.client_version", false),

  SHIFT_1(MODIFIER, GLFW_KEY_LEFT_SHIFT, "mod.shift.1"),
  SHIFT_2(MODIFIER, GLFW_KEY_RIGHT_SHIFT, "mod.shift.2"),
  CTRL_1(MODIFIER, ON_OSX ? GLFW_KEY_LEFT_SUPER : GLFW_KEY_LEFT_CONTROL, "mod.ctrl.1"),
  CTRL_2(MODIFIER, ON_OSX ? GLFW_KEY_RIGHT_SUPER : GLFW_KEY_RIGHT_CONTROL, "mod.ctrl.2"),
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

  HardcodedMapping(MappingCategory category, int keyCode, String name, String debugHelp)
  {
    this(category, keyCode, name, debugHelp, true);
  }

  HardcodedMapping(MappingCategory category, int keyCode, String name, boolean exists)
  {
    this(category, keyCode, name, null, exists);
  }

  HardcodedMapping(MappingCategory category, int keyCode, String name, String debugHelp, boolean exists)
  {
    this.category = category;
    this.keyCode = keyCode;
    this.name = name;
    this.debugHelp = category == DEBUG ? name : debugHelp;
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
    return debugHelp == null ? null : String.format("%s.help", debugHelp);
  }
}
