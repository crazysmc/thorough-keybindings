package io.github.crazysmc.thrkbs;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class PotentialKeyBinding
{
  private static final List<PotentialKeyBinding> ALL = new ArrayList<>();
  private static final Map<Integer, PotentialKeyBinding> BY_KEY = new Int2ObjectOpenHashMap<>();

  static
  {
    String misc = "key.categories.misc";
    new PotentialKeyBinding("key.gameMenu", Keyboard.KEY_ESCAPE, misc);
    new PotentialKeyBinding("key.toggleHUD", Keyboard.KEY_F1, misc);
    new PotentialKeyBinding("key.screenshot", Keyboard.KEY_F2, misc);
    new PotentialKeyBinding("key.debugInfo", Keyboard.KEY_F3, misc);
    new PotentialKeyBinding("key.togglePerspective", Keyboard.KEY_F5, misc);
    new PotentialKeyBinding("key.delayDisplayUpdate", Keyboard.KEY_F7, misc);
    new PotentialKeyBinding("key.smoothCamera", Keyboard.KEY_F8, misc);
    new PotentialKeyBinding("key.fullscreen", Keyboard.KEY_F11, misc);

    String inventory = "key.categories.inventory";
    for (int i = 0; i < 9; i++)
      new PotentialKeyBinding(String.format("key.hotbar.%d", i + 1), Keyboard.KEY_1 + i, inventory);

    String debug = "key.categories.debug";
    new PotentialKeyBinding("key.debug.reloadChunks", Keyboard.KEY_A, debug);
    new PotentialKeyBinding("key.debug.crash", Keyboard.KEY_C, debug);
    new PotentialKeyBinding("key.debug.renderDistance", Keyboard.KEY_F, debug);
    new PotentialKeyBinding("key.debug.advancedTooltips", Keyboard.KEY_H, debug);
    new PotentialKeyBinding("key.debug.pauseOnLostFocus", Keyboard.KEY_P, debug);
    new PotentialKeyBinding("key.debug.reloadResources", Keyboard.KEY_S, debug);
    new PotentialKeyBinding("key.debug.reloadTextures", Keyboard.KEY_T, debug);

    String modifier = "key.categories.modifier";
    new PotentialKeyBinding("key.mod.shift.1", Keyboard.KEY_LSHIFT, modifier);
    new PotentialKeyBinding("key.mod.shift.2", Keyboard.KEY_RSHIFT, modifier);
    new PotentialKeyBinding("key.mod.ctrl.1", Keyboard.KEY_LCONTROL, modifier);
    new PotentialKeyBinding("key.mod.ctrl.2", Keyboard.KEY_RCONTROL, modifier);
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

  public static void register(Consumer<KeyBinding> callback)
  {
    for (PotentialKeyBinding binding : ALL)
      if (binding.found)
      {
        ThoroughKeybindings.LOGGER.info("Add keybinding for {} as {}", Keyboard.getKeyName(binding.keyCode),
                                        binding.name);
        callback.accept(new CategorizedKeyBinding(binding.name, binding.keyCode, binding.category));
      }
      else // TODO remove
        ThoroughKeybindings.LOGGER.warn("{} was not added", binding.name);
  }

  public static void found(int constant)
  {
    if (constant == Keyboard.KEY_1)
      for (int i = 0; i < 9; i++)
        BY_KEY.get(Keyboard.KEY_1 + i).found = true;
    else
      Optional.ofNullable(BY_KEY.get(constant)).ifPresent(binding -> binding.found = true);
  }
}
