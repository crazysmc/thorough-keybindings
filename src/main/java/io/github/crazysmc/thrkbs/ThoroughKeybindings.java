package io.github.crazysmc.thrkbs;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.options.KeyBinding;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import net.ornithemc.osl.keybinds.api.KeyBindingEvents;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ThoroughKeybindings implements ClientModInitializer
{
  private static final Int2ObjectMap<KeyBinding> KEY_BINDINGS_MAP = new Int2ObjectArrayMap<>();
  private static final List<KeyBinding> KEY_BINDINGS_LIST = new ArrayList<>();
  private static final KeyBinding[] PROFILER_CHART_KEYS;

  static
  {
    String misc = "key.categories.misc";
    remap("key.gameMenu", Keyboard.KEY_ESCAPE, misc);
    remap("key.hideGui", Keyboard.KEY_F1, misc);
    remap("key.debug", Keyboard.KEY_F3, misc);
    remap("key.disableShader", Keyboard.KEY_F4, misc);

    String debug = "key.categories.debug";
    remap("key.reloadResources", Keyboard.KEY_T, debug);
    remap("key.reloadChunks", Keyboard.KEY_A, debug);
    remap("key.renderDistance", Keyboard.KEY_F, debug);
    remap("key.pauseOnUnfocus", Keyboard.KEY_P, debug);
    remap("key.advancedItemTooltips", Keyboard.KEY_H, debug);
    remap("key.renderHitboxes", Keyboard.KEY_B, debug);
    remap("key.debugCrash", Keyboard.KEY_C, debug);
    remap("key.clearChat", Keyboard.KEY_D, debug);
    remap("key.debugHelp", Keyboard.KEY_Q, debug);
    remap("key.toggleSpectator", Keyboard.KEY_N, debug);

    String profilerChart = "key.categories.profilerChart";
    PROFILER_CHART_KEYS = new KeyBinding[] {
        remap("key.profilerChart.1", Keyboard.KEY_1, profilerChart),
        remap("key.profilerChart.2", Keyboard.KEY_2, profilerChart),
        remap("key.profilerChart.3", Keyboard.KEY_3, profilerChart),
        remap("key.profilerChart.4", Keyboard.KEY_4, profilerChart),
        remap("key.profilerChart.5", Keyboard.KEY_5, profilerChart),
        remap("key.profilerChart.6", Keyboard.KEY_6, profilerChart),
        remap("key.profilerChart.7", Keyboard.KEY_7, profilerChart),
        remap("key.profilerChart.8", Keyboard.KEY_8, profilerChart),
        remap("key.profilerChart.9", Keyboard.KEY_9, profilerChart),
    };
    remap("key.profilerChart.back", Keyboard.KEY_0, profilerChart);

    String modifier = "key.categories.modifier";
    remap("key.mod.lshift", Keyboard.KEY_LSHIFT, modifier);
    remap("key.mod.rshift", Keyboard.KEY_RSHIFT, modifier);
    remap("key.mod.lctrl", Keyboard.KEY_LCONTROL, modifier);
    remap("key.mod.rctrl", Keyboard.KEY_RCONTROL, modifier);
    remap("key.mod.lmenu", Keyboard.KEY_LMENU, modifier);
    remap("key.mod.rmenu", Keyboard.KEY_RMENU, modifier);
  }

  private static KeyBinding remap(String name, int original, String category)
  {
    KeyBinding keyBinding = new KeyBinding(name, original, category);
    KEY_BINDINGS_MAP.put(original, keyBinding);
    KEY_BINDINGS_LIST.add(keyBinding);
    return keyBinding;
  }

  public static int getRemap(int original)
  {
    return KEY_BINDINGS_MAP.get(original).getKeyCode();
  }

  public static int getProfilerRemap(int index)
  {
    return PROFILER_CHART_KEYS[index].getKeyCode();
  }

  @Override
  public void initClient()
  {
    KeyBindingEvents.REGISTER_KEYBINDS.register(registry -> KEY_BINDINGS_LIST.forEach(registry::register));
  }
}
