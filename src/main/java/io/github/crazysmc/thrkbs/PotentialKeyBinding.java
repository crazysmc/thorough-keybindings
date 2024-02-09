package io.github.crazysmc.thrkbs;

import net.minecraft.client.options.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class PotentialKeyBinding
{
  public static final PotentialKeyBinding[] PROFILER_CHART = new PotentialKeyBinding[9];
  public static final PotentialKeyBinding[] HOTBAR = new PotentialKeyBinding[9];
  private static final List<PotentialKeyBinding> ALL = new ArrayList<>();

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

    String profilerChart = "key.categories.profilerChart";
    new PotentialKeyBinding("key.profilerChart.back", Keyboard.KEY_0, profilerChart);
    for (int i = 0; i < 9; i++)
      PROFILER_CHART[i] = new PotentialKeyBinding(String.format("key.profilerChart.%d", i + 1), Keyboard.KEY_1 + i,
                                                  profilerChart);
    for (int i = 0; i < 9; i++)
      HOTBAR[i] = new PotentialKeyBinding(String.format("key.hotbar.%d", i + 1), Keyboard.KEY_1 + i,
                                          "key.categories.inventory");
  }

  private final String name;
  private final int keyCode;
  private final String category;
  private boolean missing;
  private CategorizedKeyBinding keyBinding;

  public PotentialKeyBinding(String name, int keyCode, String category)
  {
    this.name = name;
    this.keyCode = keyCode;
    this.category = category;
    ALL.add(this);
  }

  public static void register(Set<String> keyNames, Consumer<KeyBinding> callback)
  {
    ALL.forEach(binding -> {
      if (!binding.missing && !keyNames.contains(binding.name))
      {
        ThoroughKeybindings.LOGGER.info("Add keybinding for {} as {}", Keyboard.getKeyName(binding.keyCode),
                                        binding.name);
        callback.accept(
            binding.keyBinding = new CategorizedKeyBinding(binding.name, binding.keyCode, binding.category));
      }
    });
  }

  public void setMissing()
  {
    missing = true;
  }

  public CategorizedKeyBinding getKeyBinding()
  {
    return keyBinding;
  }
}
