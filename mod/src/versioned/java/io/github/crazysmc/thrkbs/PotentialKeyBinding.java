package io.github.crazysmc.thrkbs;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class PotentialKeyBinding
{
  private static final List<PotentialKeyBinding> ALL = new ArrayList<>();
  private static final Map<Integer, PotentialKeyBinding> BY_KEY = new Int2ObjectOpenHashMap<>();
  //$if <1.13.0
  private static boolean numberKeys;
  //$if

  static
  {
    String misc = "key.categories.misc";
    String inventory = "key.categories.inventory";
    String debug = "key.categories.debug";
    String modifier = "key.categories.modifier";

    //$if <1.13.0
    new PotentialKeyBinding("key.gameMenu", org.lwjgl.input.Keyboard.KEY_ESCAPE, misc);
    new PotentialKeyBinding("key.toggleHUD", org.lwjgl.input.Keyboard.KEY_F1, misc);
    new PotentialKeyBinding("key.screenshot", org.lwjgl.input.Keyboard.KEY_F2, misc);
    new PotentialKeyBinding("key.debugInfo", org.lwjgl.input.Keyboard.KEY_F3, misc);
    new PotentialKeyBinding("key.disableShader", org.lwjgl.input.Keyboard.KEY_F4, misc);
    new PotentialKeyBinding("key.togglePerspective", org.lwjgl.input.Keyboard.KEY_F5, misc);
    new PotentialKeyBinding("key.delayDisplayUpdate", org.lwjgl.input.Keyboard.KEY_F7, misc);
    new PotentialKeyBinding("key.smoothCamera", org.lwjgl.input.Keyboard.KEY_F8, misc);
    new PotentialKeyBinding("key.fullscreen", org.lwjgl.input.Keyboard.KEY_F11, misc);

    for (int i = 0; i < 9; i++)
      new PotentialKeyBinding(String.format("key.hotbar.%d", i + 1), org.lwjgl.input.Keyboard.KEY_1 + i, inventory);

    new PotentialKeyBinding("key.debug.reloadChunks", org.lwjgl.input.Keyboard.KEY_A, debug);
    new PotentialKeyBinding("key.debug.hitboxes", org.lwjgl.input.Keyboard.KEY_B, debug);
    new PotentialKeyBinding("key.debug.crash", org.lwjgl.input.Keyboard.KEY_C, debug);
    new PotentialKeyBinding("key.debug.clearChat", org.lwjgl.input.Keyboard.KEY_D, debug);
    new PotentialKeyBinding("key.debug.renderDistance", org.lwjgl.input.Keyboard.KEY_F, debug);
    new PotentialKeyBinding("key.debug.chunkBorders", org.lwjgl.input.Keyboard.KEY_G, debug);
    new PotentialKeyBinding("key.debug.advancedTooltips", org.lwjgl.input.Keyboard.KEY_H, debug);
    new PotentialKeyBinding("key.debug.spectator", org.lwjgl.input.Keyboard.KEY_N, debug);
    new PotentialKeyBinding("key.debug.pauseOnLostFocus", org.lwjgl.input.Keyboard.KEY_P, debug);
    new PotentialKeyBinding("key.debug.help", org.lwjgl.input.Keyboard.KEY_Q, debug);
    new PotentialKeyBinding("key.debug.reloadResources", org.lwjgl.input.Keyboard.KEY_S, debug);
    new PotentialKeyBinding("key.debug.reloadTextures", org.lwjgl.input.Keyboard.KEY_T, debug);

    new PotentialKeyBinding("key.mod.shift.1", org.lwjgl.input.Keyboard.KEY_LSHIFT, modifier);
    new PotentialKeyBinding("key.mod.shift.2", org.lwjgl.input.Keyboard.KEY_RSHIFT, modifier);
    new PotentialKeyBinding("key.mod.ctrl.1", org.lwjgl.input.Keyboard.KEY_LCONTROL, modifier);
    new PotentialKeyBinding("key.mod.ctrl.2", org.lwjgl.input.Keyboard.KEY_RCONTROL, modifier);
    new PotentialKeyBinding("key.mod.alt.1", org.lwjgl.input.Keyboard.KEY_LMENU, modifier);
    new PotentialKeyBinding("key.mod.alt.2", org.lwjgl.input.Keyboard.KEY_RMENU, modifier);

    //$if >=1.13.0
    new PotentialKeyBinding("key.gameMenu", org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE, misc);
    new PotentialKeyBinding("key.toggleHUD", org.lwjgl.glfw.GLFW.GLFW_KEY_F1, misc);
    new PotentialKeyBinding("key.debugInfo", org.lwjgl.glfw.GLFW.GLFW_KEY_F3, misc);
    new PotentialKeyBinding("key.disableShader", org.lwjgl.glfw.GLFW.GLFW_KEY_F4, misc);

    new PotentialKeyBinding("key.debug.reload_chunks", org.lwjgl.glfw.GLFW.GLFW_KEY_A, debug);
    new PotentialKeyBinding("key.debug.show_hitboxes", org.lwjgl.glfw.GLFW.GLFW_KEY_B, debug);
    new PotentialKeyBinding("key.debug.copy_location", org.lwjgl.glfw.GLFW.GLFW_KEY_C, debug);
    new PotentialKeyBinding("key.debug.clear_chat", org.lwjgl.glfw.GLFW.GLFW_KEY_D, debug);
    new PotentialKeyBinding("key.debug.cycle_renderdistance", org.lwjgl.glfw.GLFW.GLFW_KEY_F, debug);
    new PotentialKeyBinding("key.debug.chunk_boundaries", org.lwjgl.glfw.GLFW.GLFW_KEY_G, debug);
    new PotentialKeyBinding("key.debug.advanced_tooltips", org.lwjgl.glfw.GLFW.GLFW_KEY_H, debug);
    new PotentialKeyBinding("key.debug.inspect", org.lwjgl.glfw.GLFW.GLFW_KEY_I, debug);
    new PotentialKeyBinding("key.debug.creative_spectator", org.lwjgl.glfw.GLFW.GLFW_KEY_N, debug);
    new PotentialKeyBinding("key.debug.pause_focus", org.lwjgl.glfw.GLFW.GLFW_KEY_P, debug);
    new PotentialKeyBinding("key.debug.help", org.lwjgl.glfw.GLFW.GLFW_KEY_Q, debug);
    new PotentialKeyBinding("key.debug.reload_resourcepacks", org.lwjgl.glfw.GLFW.GLFW_KEY_T, debug);

    new PotentialKeyBinding("key.mod.shift.1", org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT, modifier);
    new PotentialKeyBinding("key.mod.shift.2", org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT, modifier);
    new PotentialKeyBinding("key.mod.ctrl.1", org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL, modifier);
    new PotentialKeyBinding("key.mod.ctrl.2", org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_CONTROL, modifier);
    new PotentialKeyBinding("key.mod.alt.1", org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_ALT, modifier);
    new PotentialKeyBinding("key.mod.alt.2", org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_ALT, modifier);
    //$if
  }

  private final String name;
  private final int keyCode;
  private final String category;
  private boolean found;

  public PotentialKeyBinding(String name, int keyCode, String category)
  {
    this.name = name;
    this.keyCode = keyCode;
    this.category = category;
    ALL.add(this);
    BY_KEY.put(keyCode, this);
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

  public static Stream<PotentialKeyBinding> getFoundBindings()
  {
    return ALL.stream().filter(binding -> binding.found);
  }

  public static void found(int constant)
  {
    if (constant == 9) // loop condition
      return;
    //$if <1.13.0
    if (constant == org.lwjgl.input.Keyboard.KEY_1)
    {
      if (!numberKeys)
        numberKeys = true; // need twice to have profiler chart AND hotbar keys
      else
        for (int i = 0; i < 9; i++)
          BY_KEY.get(org.lwjgl.input.Keyboard.KEY_1 + i).found = true;
      return;
    }
    //$if
    PotentialKeyBinding binding = BY_KEY.get(constant);
    if (binding != null)
      binding.found = true;
  }
}
