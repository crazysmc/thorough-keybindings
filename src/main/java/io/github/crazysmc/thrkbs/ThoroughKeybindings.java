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
  private static final KeyBinding[] HOTBAR_KEYS;
  private static final KeyBinding[] PROFILER_CHART_KEYS;

  static
  {
    remap("key.gameMenu", Keyboard.KEY_ESCAPE);
    remap("key.hideGui", Keyboard.KEY_F1);
    remap("key.screenshot", Keyboard.KEY_F2);
    remap("key.debug", Keyboard.KEY_F3);
    remap("key.togglePerspective", Keyboard.KEY_F5);
    remap("key.smoothCamera", Keyboard.KEY_F8);
    remap("key.fullscreen", Keyboard.KEY_F11);

    remap("key.debug.reloadTextures", Keyboard.KEY_T);
    remap("key.debug.reloadChunks", Keyboard.KEY_A);
    remap("key.debug.reloadResources", Keyboard.KEY_S);
    remap("key.debug.renderDistance", Keyboard.KEY_F);

    HOTBAR_KEYS = new KeyBinding[] {
        remap("key.hotbar.1", Keyboard.KEY_1),
        remap("key.hotbar.2", Keyboard.KEY_2),
        remap("key.hotbar.3", Keyboard.KEY_3),
        remap("key.hotbar.4", Keyboard.KEY_4),
        remap("key.hotbar.5", Keyboard.KEY_5),
        remap("key.hotbar.6", Keyboard.KEY_6),
        remap("key.hotbar.7", Keyboard.KEY_7),
        remap("key.hotbar.8", Keyboard.KEY_8),
        remap("key.hotbar.9", Keyboard.KEY_9),
    };

    remap("key.profilerChart.back", Keyboard.KEY_0);
    PROFILER_CHART_KEYS = new KeyBinding[] {
        remap("key.profilerChart.1", Keyboard.KEY_1),
        remap("key.profilerChart.2", Keyboard.KEY_2),
        remap("key.profilerChart.3", Keyboard.KEY_3),
        remap("key.profilerChart.4", Keyboard.KEY_4),
        remap("key.profilerChart.5", Keyboard.KEY_5),
        remap("key.profilerChart.6", Keyboard.KEY_6),
        remap("key.profilerChart.7", Keyboard.KEY_7),
        remap("key.profilerChart.8", Keyboard.KEY_8),
        remap("key.profilerChart.9", Keyboard.KEY_9),
    };
  }

  private static KeyBinding remap(String name, int original)
  {
    KeyBinding keyBinding = new KeyBinding(name, original);
    KEY_BINDINGS_MAP.put(original, keyBinding);
    KEY_BINDINGS_LIST.add(keyBinding);
    return keyBinding;
  }

  public static int getRemap(int original)
  {
    return KEY_BINDINGS_MAP.get(original).keyCode;
  }

  public static int getHotbarRemap(int index)
  {
    return HOTBAR_KEYS[index].keyCode;
  }

  public static int getProfilerRemap(int index)
  {
    return PROFILER_CHART_KEYS[index].keyCode;
  }

  @Override
  public void initClient()
  {
    KeyBindingEvents.REGISTER_KEYBINDS.register(registry -> KEY_BINDINGS_LIST.forEach(registry::register));
  }
}
