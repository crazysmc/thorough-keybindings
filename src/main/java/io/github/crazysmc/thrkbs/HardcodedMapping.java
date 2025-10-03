package io.github.crazysmc.thrkbs;

import static io.github.crazysmc.thrkbs.MappingCategory.*;
import static org.lwjgl.glfw.GLFW.*;

public enum HardcodedMapping
{
  GAME_MENU(MISC, GLFW_KEY_ESCAPE, "key.gameMenu", false),
  TOGGLE_HUD(MISC, GLFW_KEY_F1, "key.toggleHUD", false),
  DEBUG_INFO(MISC, GLFW_KEY_F3, "key.debugInfo", false),
  DISABLE_SHADER(MISC, GLFW_KEY_F4, "key.disableShader"),

  CHARTS_PROFILER(DEBUG, GLFW_KEY_1, "key.debug.charts.profiler"),
  CHARTS_FPS(DEBUG, GLFW_KEY_2, "key.debug.charts.fps"),
  CHARTS_NETWORK(DEBUG, GLFW_KEY_3, "key.debug.charts.network"),
  RELOAD_CHUNKS(DEBUG, GLFW_KEY_A, "key.debug.reload_chunks"),
  SHOW_HITBOXES(DEBUG, GLFW_KEY_B, "key.debug.show_hitboxes"),
  COPY_LOCATION(DEBUG, GLFW_KEY_C, "key.debug.copy_location"),
  CLEAR_CHAT(DEBUG, GLFW_KEY_D, "key.debug.clear_chat"),
  CYCLE_RENDERDISTANCE(DEBUG, GLFW_KEY_F, "key.debug.cycle_renderdistance"),
  CHUNK_BOUNDARIES(DEBUG, GLFW_KEY_G, "key.debug.chunk_boundaries"),
  ADVANCED_TOOLTIPS(DEBUG, GLFW_KEY_H, "key.debug.advanced_tooltips"),
  INSPECT(DEBUG, GLFW_KEY_I, "key.debug.inspect"),
  PROFILING(DEBUG, GLFW_KEY_L, "key.debug.profiling"),
  CREATIVE_SPECTATOR(DEBUG, GLFW_KEY_N, "key.debug.creative_spectator"),
  PAUSE_FOCUS(DEBUG, GLFW_KEY_P, "key.debug.pause_focus"),
  HELP(DEBUG, GLFW_KEY_Q, "key.debug.help"),
  DUMP_DYNAMIC_TEXTURES(DEBUG, GLFW_KEY_S, "key.debug.dump_dynamic_textures"),
  RELOAD_RESOURCEPACKS(DEBUG, GLFW_KEY_T, "key.debug.reload_resourcepacks"),
  CLIENT_VERSION(DEBUG, GLFW_KEY_V, "key.debug.client_version"),

  SHIFT_1(MODIFIER, GLFW_KEY_LEFT_SHIFT, "key.mod.shift.1"),
  SHIFT_2(MODIFIER, GLFW_KEY_RIGHT_SHIFT, "key.mod.shift.2"),
  CTRL_1(MODIFIER, GLFW_KEY_LEFT_CONTROL, "key.mod.ctrl.1"),
  CTRL_2(MODIFIER, GLFW_KEY_RIGHT_CONTROL, "key.mod.ctrl.2"),
  ALT_1(MODIFIER, GLFW_KEY_LEFT_ALT, "key.mod.alt.1"),
  ALT_2(MODIFIER, GLFW_KEY_RIGHT_ALT, "key.mod.alt.2"),
  ;

  private final String category;
  private final int keyCode;
  private final String name;
  private final boolean heldDown; // XXX no that is not it…

  HardcodedMapping(String category, int keyCode, String name)
  {
    this(category, keyCode, name, true);
  }

  HardcodedMapping(String category, int keyCode, String name, boolean heldDown)
  {
    this.category = category;
    this.keyCode = keyCode;
    this.name = name;
    this.heldDown = heldDown;
  }

  public String getName()
  {
    return name;
  }

  public int getKeyCode()
  {
    return keyCode;
  }

  public String getCategory()
  {
    return category;
  }

  public boolean isHeldDown()
  {
    return heldDown;
  }
}
