package io.github.crazysmc.thrkbs;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PotentialKeyBinding
{
  private static final List<PotentialKeyBinding> ALL = new ArrayList<>();
  private static final Map<Integer, PotentialKeyBinding> BY_KEY = new Int2ObjectOpenHashMap<>();

  static
  {
    String misc = "key.categories.misc";
    String debug = "key.categories.debug";
    String modifier = "key.categories.modifier";

    new PotentialKeyBinding("key.gameMenu", GLFW.GLFW_KEY_ESCAPE, misc);
    new PotentialKeyBinding("key.toggleHUD", GLFW.GLFW_KEY_F1, misc);
    new PotentialKeyBinding("key.debugInfo", GLFW.GLFW_KEY_F3, misc);
    new PotentialKeyBinding("key.disableShader", GLFW.GLFW_KEY_F4, misc); // TODO also game mode switcher

    new PotentialKeyBinding("key.debug.reload_chunks", GLFW.GLFW_KEY_A, debug);
    new PotentialKeyBinding("key.debug.show_hitboxes", GLFW.GLFW_KEY_B, debug);
    new PotentialKeyBinding("key.debug.copy_location", GLFW.GLFW_KEY_C, debug);
    new PotentialKeyBinding("key.debug.clear_chat", GLFW.GLFW_KEY_D, debug);
    new PotentialKeyBinding("key.debug.cycle_renderdistance", GLFW.GLFW_KEY_F, debug);
    new PotentialKeyBinding("key.debug.chunk_boundaries", GLFW.GLFW_KEY_G, debug);
    new PotentialKeyBinding("key.debug.advanced_tooltips", GLFW.GLFW_KEY_H, debug);
    new PotentialKeyBinding("key.debug.inspect", GLFW.GLFW_KEY_I, debug);
    new PotentialKeyBinding("key.debug.creative_spectator", GLFW.GLFW_KEY_N, debug);
    new PotentialKeyBinding("key.debug.pause_focus", GLFW.GLFW_KEY_P, debug);
    new PotentialKeyBinding("key.debug.help", GLFW.GLFW_KEY_Q, debug);
    new PotentialKeyBinding("key.debug.reload_resourcepacks", GLFW.GLFW_KEY_T, debug);

    new PotentialKeyBinding("key.mod.shift.1", GLFW.GLFW_KEY_LEFT_SHIFT, modifier);
    new PotentialKeyBinding("key.mod.shift.2", GLFW.GLFW_KEY_RIGHT_SHIFT, modifier);
    new PotentialKeyBinding("key.mod.ctrl.1", GLFW.GLFW_KEY_LEFT_CONTROL, modifier);
    new PotentialKeyBinding("key.mod.ctrl.2", GLFW.GLFW_KEY_RIGHT_CONTROL, modifier);
    new PotentialKeyBinding("key.mod.alt.1", GLFW.GLFW_KEY_LEFT_ALT, modifier);
    new PotentialKeyBinding("key.mod.alt.2", GLFW.GLFW_KEY_RIGHT_ALT, modifier);
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

  public static List<PotentialKeyBinding> foundBindings()
  {
    return ALL.stream().filter(binding -> binding.found).collect(Collectors.toList());
  }

  public static void found(int constant)
  {
    if (constant == 9) // loop condition
      return;
    PotentialKeyBinding binding = BY_KEY.get(constant);
    if (binding != null)
      binding.found = true;
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
