package io.github.crazysmc.thrkbs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.crazysmc.thrkbs.Constants.INIT_DEBUG_MSG;
import static io.github.crazysmc.thrkbs.HardcodedMapping.*;

public class InitVersion implements ClientModInitializer
{
  public static final HardcodedMapping[] KEYBOARD_HANDLER_MAPPINGS = {
      GAME_MENU, TOGGLE_HUD, DEBUG_INFO, DISABLE_SHADER,

      SHOW_HITBOXES,

      PROFILER_0,
      PROFILER_1, PROFILER_2, PROFILER_3, PROFILER_4, PROFILER_5, PROFILER_6, PROFILER_7, PROFILER_8, PROFILER_9,
  };
  private static final Logger LOGGER = LogManager.getLogger();
  private static final HardcodedMapping[] KNOWN_MAPPINGS = {
      GAME_MENU, TOGGLE_HUD, DEBUG_INFO, DISABLE_SHADER,

      RELOAD_CHUNKS, SHOW_HITBOXES, COPY_LOCATION, CLEAR_CHAT, CYCLE_RENDERDISTANCE, CHUNK_BOUNDARIES,
      ADVANCED_TOOLTIPS, INSPECT, CREATIVE_SPECTATOR, PAUSE_FOCUS, HELP, RELOAD_RESOURCEPACKS,

      SHIFT_1, SHIFT_2, CTRL_1, CTRL_2, ALT_1, ALT_2,

      PROFILER_0,
      PROFILER_1, PROFILER_2, PROFILER_3, PROFILER_4, PROFILER_5, PROFILER_6, PROFILER_7, PROFILER_8, PROFILER_9,
  };

  private static KeyMapping getKeyMapping(HardcodedMapping mapping)
  {
    return new KeyMapping(mapping.getName(), mapping.getKeyCode(), mapping.getCategory().getId());
  }

  @Override
  public void onInitializeClient()
  {
    for (HardcodedMapping mapping : KNOWN_MAPPINGS)
      mapping.object = KeyBindingHelper.registerKeyBinding(getKeyMapping(mapping));
    LOGGER.debug(INIT_DEBUG_MSG, KNOWN_MAPPINGS.length);
  }
}
