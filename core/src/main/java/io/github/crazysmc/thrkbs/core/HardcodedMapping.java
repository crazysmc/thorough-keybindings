package io.github.crazysmc.thrkbs.core;

import java.util.EnumSet;

import static org.lwjgl.glfw.GLFW.*;

public enum HardcodedMapping
{
  GAME_MENU("key.gameMenu", GLFW_KEY_ESCAPE, "key.categories.misc"),
  TOGGLE_HUD("key.toggleHUD", GLFW_KEY_F1, "key.categories.misc"),
  DEBUG_INFO("key.debugInfo", GLFW_KEY_F3, "key.categories.misc"),
  DISABLE_SHADER("key.disableShader", GLFW_KEY_F4, "key.categories.misc"),

  CHARTS_PROFILER("key.debug.charts.profiler", GLFW_KEY_1, "key.categories.debug"),
  CHARTS_FPS("key.debug.charts.fps", GLFW_KEY_2, "key.categories.debug"),
  CHARTS_NETWORK("key.debug.charts.network", GLFW_KEY_3, "key.categories.debug"),
  RELOAD_CHUNKS("key.debug.reload_chunks", GLFW_KEY_A, "key.categories.debug"),
  SHOW_HITBOXES("key.debug.show_hitboxes", GLFW_KEY_B, "key.categories.debug"),
  COPY_LOCATION("key.debug.copy_location", GLFW_KEY_C, "key.categories.debug"),
  CLEAR_CHAT("key.debug.clear_chat", GLFW_KEY_D, "key.categories.debug"),
  CYCLE_RENDERDISTANCE("key.debug.cycle_renderdistance", GLFW_KEY_F, "key.categories.debug"),
  CHUNK_BOUNDARIES("key.debug.chunk_boundaries", GLFW_KEY_G, "key.categories.debug"),
  ADVANCED_TOOLTIPS("key.debug.advanced_tooltips", GLFW_KEY_H, "key.categories.debug"),
  INSPECT("key.debug.inspect", GLFW_KEY_I, "key.categories.debug"),
  PROFILING("key.debug.profiling", GLFW_KEY_L, "key.categories.debug"),
  CREATIVE_SPECTATOR("key.debug.creative_spectator", GLFW_KEY_N, "key.categories.debug"),
  PAUSE_FOCUS("key.debug.pause_focus", GLFW_KEY_P, "key.categories.debug"),
  HELP("key.debug.help", GLFW_KEY_Q, "key.categories.debug"),
  DUMP_DYNAMIC_TEXTURES("key.debug.dump_dynamic_textures", GLFW_KEY_S, "key.categories.debug"),
  RELOAD_RESOURCEPACKS("key.debug.reload_resourcepacks", GLFW_KEY_T, "key.categories.debug"),

  SHIFT_1("key.mod.shift.1", GLFW_KEY_LEFT_SHIFT, "key.categories.modifier"),
  SHIFT_2("key.mod.shift.2", GLFW_KEY_RIGHT_SHIFT, "key.categories.modifier"),
  CTRL_1("key.mod.ctrl.1", GLFW_KEY_LEFT_CONTROL, "key.categories.modifier"),
  CTRL_2("key.mod.ctrl.2", GLFW_KEY_RIGHT_CONTROL, "key.categories.modifier"),
  ALT_1("key.mod.alt.1", GLFW_KEY_LEFT_ALT, "key.categories.modifier"),
  ALT_2("key.mod.alt.2", GLFW_KEY_RIGHT_ALT, "key.categories.modifier"),
  ;

  public static final String DEBUG_CATEGORY = "key.categories.debug";
  public static final String MODIFIER_CATEGORY = "key.categories.modifier";
  public static final int[] DEBUG_KEYS =
      EnumSet.range(DISABLE_SHADER, RELOAD_RESOURCEPACKS).stream().mapToInt(HardcodedMapping::getKeyCode).toArray();

  private final String name;
  private final int keyCode;
  private final String category;

  HardcodedMapping(String name, int keyCode, String category)
  {
    this.name = name;
    this.keyCode = keyCode;
    this.category = category;
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
}
