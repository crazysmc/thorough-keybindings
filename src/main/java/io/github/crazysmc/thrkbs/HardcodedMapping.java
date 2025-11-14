package io.github.crazysmc.thrkbs;

import java.util.Arrays;
import java.util.stream.Stream;

import static io.github.crazysmc.thrkbs.MappingCategory.*;
import static io.github.crazysmc.thrkbs.ThoroughKeybindings.ON_OSX;
import static org.lwjgl.glfw.GLFW.*;

public enum HardcodedMapping
{
  GAME_MENU(MISC, GLFW_KEY_ESCAPE, "gameMenu", "pause", true),
  TOGGLE_GUI(MISC, GLFW_KEY_F1, "toggleGui"),
  DEBUG_INFO(MISC, GLFW_KEY_F3, "debug.modifier"),
  TOGGLE_SHADER(MISC, GLFW_KEY_F4, "toggleSpectatorShaderEffects"),

  RELOAD_CHUNKS(DEBUG, GLFW_KEY_A, "reloadChunk", "reload_chunks", true),
  SHOW_HITBOXES(DEBUG, GLFW_KEY_B, "showHitboxes", "show_hitboxes", true),
  COPY_LOCATION(DEBUG, GLFW_KEY_C, "copyLocation", "copy_location", true),
  CLEAR_CHAT(DEBUG, GLFW_KEY_D, "clearChat", "clear_chat", true),
  RENDER_DISTANCE(DEBUG, GLFW_KEY_F, "cycleRenderDistance", "cycle_renderdistance", true),
  CHUNK_BOUNDARIES(DEBUG, GLFW_KEY_G, "showChunkBorders", "chunk_boundaries", true),
  ADVANCED_TOOLTIPS(DEBUG, GLFW_KEY_H, "showAdvancedTooltips", "advanced_tooltips", true),
  INSPECT(DEBUG, GLFW_KEY_I, "copyRecreateCommand", "inspect", true),
  SPECTATE(DEBUG, GLFW_KEY_N, "spectate", "creative_spectator", true),
  FOCUS_PAUSE(DEBUG, GLFW_KEY_P, "focusPause", "pause_focus", true),
  HELP(DEBUG, GLFW_KEY_Q, "help", "help", true),
  RELOAD_RESOURCE_PACKS(DEBUG, GLFW_KEY_T, "reloadResourcePacks", "reload_resourcepacks", true),

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
