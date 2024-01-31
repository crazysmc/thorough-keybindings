package io.github.crazysmc.thrkbs;

import net.ornithemc.osl.config.api.ConfigManager;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final String MOD_ID = "thorough-keybindings";

  @Override
  public void initClient()
  {
    ConfigManager.register(new ModConfig());
  }
}
