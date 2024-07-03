package io.github.crazysmc.thrkbs.keycodes;

import io.github.crazysmc.thrkbs.core.api.HardcodedMapping;

import java.util.EnumSet;

import static org.lwjgl.input.Keyboard.*;

public enum HardcodedMappingImpl implements HardcodedMapping
{
  GAME_MENU("key.gameMenu", KEY_ESCAPE, "key.categories.misc"),
  TOGGLE_HUD("key.toggleHUD", KEY_F1, "key.categories.misc"),
  SCREENSHOT("key.screenshot", KEY_F2, "key.categories.misc"),
  DEBUG_INFO("key.debugInfo", KEY_F3, "key.categories.misc"),
  DISABLE_SHADER("key.disableShader", KEY_F4, "key.categories.misc"),
  TOGGLE_PERSPECTIVE("key.togglePerspective", KEY_F5, "key.categories.misc"),
  DELAY_DISPLAY_UPDATE("key.delayDisplayUpdate", KEY_F7, "key.categories.misc"),
  SMOOTH_CAMERA("key.smoothCamera", KEY_F8, "key.categories.misc"),
  FULLSCREEN("key.fullscreen", KEY_F11, "key.categories.misc"),

  HOTBAR_1("key.hotbar.1", KEY_1, "key.categories.hotbar"),
  HOTBAR_2("key.hotbar.2", KEY_2, "key.categories.hotbar"),
  HOTBAR_3("key.hotbar.3", KEY_3, "key.categories.hotbar"),
  HOTBAR_4("key.hotbar.4", KEY_4, "key.categories.hotbar"),
  HOTBAR_5("key.hotbar.5", KEY_5, "key.categories.hotbar"),
  HOTBAR_6("key.hotbar.6", KEY_6, "key.categories.hotbar"),
  HOTBAR_7("key.hotbar.7", KEY_7, "key.categories.hotbar"),
  HOTBAR_8("key.hotbar.8", KEY_8, "key.categories.hotbar"),
  HOTBAR_9("key.hotbar.9", KEY_9, "key.categories.hotbar"),

  RELOAD_CHUNKS("key.debug.reloadChunks", KEY_A, "key.categories.debug"),
  SHOW_HITBOXES("key.debug.hitboxes", KEY_B, "key.categories.debug"),
  CRASH("key.debug.crash", KEY_C, "key.categories.debug"),
  CLEAR_CHAT("key.debug.clearChat", KEY_D, "key.categories.debug"),
  RENDER_DISTANCE("key.debug.renderDistance", KEY_F, "key.categories.debug"),
  CHUNK_BORDERS("key.debug.chunkBorders", KEY_G, "key.categories.debug"),
  ADVANCED_TOOLTIPS("key.debug.advancedTooltips", KEY_H, "key.categories.debug"),
  SPECTATOR("key.debug.spectator", KEY_N, "key.categories.debug"),
  PAUSE_FOCUS("key.debug.pauseOnLostFocus", KEY_P, "key.categories.debug"),
  HELP("key.debug.help", KEY_Q, "key.categories.debug"),
  RELOAD_RESOURCES("key.debug.reloadResources", KEY_S, "key.categories.debug"),
  RELOAD_TEXTURES("key.debug.reloadTextures", KEY_T, "key.categories.debug"),

  SHIFT_1("key.mod.shift.1", KEY_LSHIFT, "key.categories.modifier"),
  SHIFT_2("key.mod.shift.2", KEY_RSHIFT, "key.categories.modifier"),
  CTRL_1("key.mod.ctrl.1", KEY_LCONTROL, "key.categories.modifier"),
  CTRL_2("key.mod.ctrl.2", KEY_RCONTROL, "key.categories.modifier"),
  ALT_1("key.mod.alt.1", KEY_LMENU, "key.categories.modifier"),
  ALT_2("key.mod.alt.2", KEY_RMENU, "key.categories.modifier"),
  ;

  public static final int[] DEBUG_KEYS =
      EnumSet.range(RELOAD_CHUNKS, RELOAD_TEXTURES).stream().mapToInt(HardcodedMappingImpl::getKeyCode).toArray();

  private final String name;
  private final int keyCode;
  private final String category;

  HardcodedMappingImpl(String name, int keyCode, String category)
  {
    this.name = name;
    this.keyCode = keyCode;
    this.category = category;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public int getKeyCode()
  {
    return keyCode;
  }

  @Override
  public String getCategory()
  {
    return category;
  }
}
