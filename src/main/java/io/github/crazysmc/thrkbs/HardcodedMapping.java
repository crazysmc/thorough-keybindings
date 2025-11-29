package io.github.crazysmc.thrkbs;

import java.util.Arrays;
import java.util.stream.Stream;

import static io.github.crazysmc.thrkbs.KeyboardConstants.*;
import static io.github.crazysmc.thrkbs.MappingCategory.*;
import static io.github.crazysmc.thrkbs.ThoroughKeybindings.*;
import static io.github.crazysmc.thrkbs.Versions.V113PRE6;
import static io.github.crazysmc.thrkbs.Versions.V18W11A;
import static org.lwjgl.glfw.GLFW.*;

public enum HardcodedMapping
{
  GAME_MENU(MISC, NO_GLFW ? KEY_ESCAPE : GLFW_KEY_ESCAPE, "gameMenu", "pause"),
  TOGGLE_GUI(MISC, NO_GLFW ? KEY_F1 : GLFW_KEY_F1, "toggleGui"),
  DEBUG_INFO(MISC, NO_GLFW ? KEY_F3 : GLFW_KEY_F3, "debug.modifier"),
  TOGGLE_SHADER(MISC, NO_GLFW ? KEY_F4 : GLFW_KEY_F4, "toggleSpectatorShaderEffects"),

  RELOAD_CHUNKS(DEBUG, NO_GLFW ? KEY_A : GLFW_KEY_A, "reloadChunk", "reload_chunks"),
  SHOW_HITBOXES(DEBUG, NO_GLFW ? KEY_B : GLFW_KEY_B, "showHitboxes", "show_hitboxes"),
  CRASH(DEBUG, NO_GLFW ? KEY_C : GLFW_KEY_C, "crash", null, MC_VERSION.compareTo(V18W11A) < 0),
  COPY_LOCATION(DEBUG, GLFW_KEY_C, "copyLocation", "copy_location", MC_VERSION.compareTo(V18W11A) >= 0),
  CLEAR_CHAT(DEBUG, NO_GLFW ? KEY_D : GLFW_KEY_D, "clearChat", "clear_chat"),
  RENDER_DISTANCE(DEBUG, NO_GLFW ? KEY_F : GLFW_KEY_F, "cycleRenderDistance", "cycle_renderdistance"),
  CHUNK_BOUNDARIES(DEBUG, NO_GLFW ? KEY_G : GLFW_KEY_G, "showChunkBorders", "chunk_boundaries"),
  ADVANCED_TOOLTIPS(DEBUG, NO_GLFW ? KEY_H : GLFW_KEY_H, "showAdvancedTooltips", "advanced_tooltips"),
  INSPECT(DEBUG, GLFW_KEY_I, "copyRecreateCommand", "inspect", MC_VERSION.compareTo(V113PRE6) >= 0),
  SPECTATE(DEBUG, NO_GLFW ? KEY_N : GLFW_KEY_N, "spectate", "creative_spectator"),
  FOCUS_PAUSE(DEBUG, NO_GLFW ? KEY_P : GLFW_KEY_P, "focusPause", "pause_focus"),
  HELP(DEBUG, NO_GLFW ? KEY_Q : GLFW_KEY_Q, "help", "help"),
  RELOAD_RESOURCE_PACKS(DEBUG, NO_GLFW ? KEY_T : GLFW_KEY_T, "reloadResourcePacks", "reload_resourcepacks"),

  SHIFT_1(MODIFIER, NO_GLFW ? KEY_LSHIFT : GLFW_KEY_LEFT_SHIFT, "mod.shift.1"),
  SHIFT_2(MODIFIER, NO_GLFW ? KEY_RSHIFT : GLFW_KEY_RIGHT_SHIFT, "mod.shift.2"),
  CTRL_1(MODIFIER,
         NO_GLFW ? (ON_OSX ? KEY_LMETA : KEY_LCONTROL) : (ON_OSX ? GLFW_KEY_LEFT_SUPER : GLFW_KEY_LEFT_CONTROL),
         "mod.ctrl.1"),
  CTRL_2(MODIFIER,
         NO_GLFW ? (ON_OSX ? KEY_RMETA : KEY_RCONTROL) : (ON_OSX ? GLFW_KEY_RIGHT_SUPER : GLFW_KEY_RIGHT_CONTROL),
         "mod.ctrl.2"),
  ALT_1(MODIFIER, NO_GLFW ? KEY_LMENU : GLFW_KEY_LEFT_ALT, "mod.alt.1"),
  ALT_2(MODIFIER, NO_GLFW ? KEY_RMENU : GLFW_KEY_RIGHT_ALT, "mod.alt.2"),

  PROFILER_0(PROFILER, NO_GLFW ? KEY_0 : GLFW_KEY_0, "profiler.up"),
  PROFILER_1(PROFILER, NO_GLFW ? KEY_1 : GLFW_KEY_1, "profiler.1"),
  PROFILER_2(PROFILER, NO_GLFW ? KEY_2 : GLFW_KEY_2, "profiler.2"),
  PROFILER_3(PROFILER, NO_GLFW ? KEY_3 : GLFW_KEY_3, "profiler.3"),
  PROFILER_4(PROFILER, NO_GLFW ? KEY_4 : GLFW_KEY_4, "profiler.4"),
  PROFILER_5(PROFILER, NO_GLFW ? KEY_5 : GLFW_KEY_5, "profiler.5"),
  PROFILER_6(PROFILER, NO_GLFW ? KEY_6 : GLFW_KEY_6, "profiler.6"),
  PROFILER_7(PROFILER, NO_GLFW ? KEY_7 : GLFW_KEY_7, "profiler.7"),
  PROFILER_8(PROFILER, NO_GLFW ? KEY_8 : GLFW_KEY_8, "profiler.8"),
  PROFILER_9(PROFILER, NO_GLFW ? KEY_9 : GLFW_KEY_9, "profiler.9"),
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
