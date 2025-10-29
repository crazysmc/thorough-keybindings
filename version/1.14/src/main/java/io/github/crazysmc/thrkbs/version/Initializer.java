package io.github.crazysmc.thrkbs.version;

import io.github.crazysmc.thrkbs.HardcodedMapping;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import static io.github.crazysmc.thrkbs.Constants.INIT_DEBUG_MSG;
import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.ThoroughKeybindings.LOGGER;

public class Initializer implements ClientModInitializer
{
  private static final HardcodedMapping[] KNOWN_MAPPINGS = {
      GAME_MENU, TOGGLE_HUD, DEBUG_INFO, DISABLE_SHADER,

      RELOAD_CHUNKS, SHOW_HITBOXES, COPY_LOCATION, CLEAR_CHAT, CYCLE_RENDERDISTANCE, CHUNK_BOUNDARIES,
      ADVANCED_TOOLTIPS, INSPECT, CREATIVE_SPECTATOR, PAUSE_FOCUS, HELP, RELOAD_RESOURCEPACKS,

      SHIFT_1, SHIFT_2, CTRL_1, CTRL_2, ALT_1, ALT_2,

      PROFILER_0,
      PROFILER_1, PROFILER_2, PROFILER_3, PROFILER_4, PROFILER_5, PROFILER_6, PROFILER_7, PROFILER_8, PROFILER_9,
  };

  @Override
  public void onInitializeClient()
  {
    for (HardcodedMapping mapping : KNOWN_MAPPINGS)
      KeyBindingHelper.registerKeyBinding(new KeyRemapping(mapping));
    LOGGER.debug(INIT_DEBUG_MSG, KNOWN_MAPPINGS.length);
  }
}
