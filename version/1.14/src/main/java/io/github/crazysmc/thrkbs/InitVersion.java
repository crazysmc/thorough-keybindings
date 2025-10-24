package io.github.crazysmc.thrkbs;

import io.github.crazysmc.thrkbs.mixin.KeyMappingAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static io.github.crazysmc.thrkbs.Constants.INIT_DEBUG_MSG;
import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static java.util.Collections.unmodifiableMap;

public class InitVersion implements ClientModInitializer
{
  // maybe TODO move to usage place
  public static final HardcodedMapping[] KEYBOARD_HANDLER_MAPPINGS = {
      GAME_MENU, TOGGLE_HUD, DEBUG_INFO, DISABLE_SHADER,

      SHOW_HITBOXES,

      PROFILER_0,
      PROFILER_1, PROFILER_2, PROFILER_3, PROFILER_4, PROFILER_5, PROFILER_6, PROFILER_7, PROFILER_8, PROFILER_9,
  };
  public static final HardcodedMapping[] DEBUG_KEYS_MAPPINGS = {
      RELOAD_CHUNKS, SHOW_HITBOXES, COPY_LOCATION, CLEAR_CHAT, CYCLE_RENDERDISTANCE, CHUNK_BOUNDARIES,
      ADVANCED_TOOLTIPS, INSPECT, CREATIVE_SPECTATOR, PAUSE_FOCUS, HELP, RELOAD_RESOURCEPACKS,
  };
  public static final Map<String, HardcodedMapping> DEBUG_KEYS_HELP;

  private static final Logger LOGGER = LogManager.getLogger();
  private static final HardcodedMapping[] KNOWN_MAPPINGS = {
      GAME_MENU, TOGGLE_HUD, DEBUG_INFO, DISABLE_SHADER,

      RELOAD_CHUNKS, SHOW_HITBOXES, COPY_LOCATION, CLEAR_CHAT, CYCLE_RENDERDISTANCE, CHUNK_BOUNDARIES,
      ADVANCED_TOOLTIPS, INSPECT, CREATIVE_SPECTATOR, PAUSE_FOCUS, HELP, RELOAD_RESOURCEPACKS,

      SHIFT_1, SHIFT_2, CTRL_1, CTRL_2, ALT_1, ALT_2,

      PROFILER_0,
      PROFILER_1, PROFILER_2, PROFILER_3, PROFILER_4, PROFILER_5, PROFILER_6, PROFILER_7, PROFILER_8, PROFILER_9,
  };
  private static final Int2ObjectMap<HardcodedMapping> KEY_DOWN_MAP = new Int2ObjectOpenHashMap<>(7);//TODO move

  static
  {
    HashMap<String, HardcodedMapping> debugKeysHelp = new HashMap<>(1 + DEBUG_KEYS_MAPPINGS.length);
    debugKeysHelp.put("debug.pause.help", GAME_MENU);
    for (HardcodedMapping mapping : DEBUG_KEYS_MAPPINGS)
      debugKeysHelp.put(String.format("%s.help", mapping.getName()), mapping);
    DEBUG_KEYS_HELP = unmodifiableMap(debugKeysHelp);
  }

  private final Map<String, KeyMapping> keyMappingMap = KeyMappingAccessor.getAll();

  public static HardcodedMapping getMapping(int defaultKey)
  {
    return KEY_DOWN_MAP.get(defaultKey);
  }

  private KeyMapping getKeyMapping(HardcodedMapping mapping)
  {
    String name = mapping.getId();
    KeyMapping keyMapping = new KeyMapping(name, mapping.getKeyCode(), mapping.getCategory().getId());
    keyMappingMap.remove(name);
    if (mapping.requiresToken())
      KEY_DOWN_MAP.put(mapping.getKeyCode(), mapping);
    return keyMapping;
  }

  @Override
  public void onInitializeClient()
  {
    for (HardcodedMapping mapping : KNOWN_MAPPINGS)
      mapping.object = KeyBindingHelper.registerKeyBinding(getKeyMapping(mapping));
    KeyMapping.resetMapping();
    LOGGER.debug(INIT_DEBUG_MSG, KNOWN_MAPPINGS.length);
  }
}
