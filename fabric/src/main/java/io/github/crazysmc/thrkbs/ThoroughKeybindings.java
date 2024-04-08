package io.github.crazysmc.thrkbs;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.KeyboardHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final String MOD_ID = "thorough-keybindings";
  public static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");

  @Override
  public void onInitializeClient()
  {
    Class<?> forceLoad = KeyboardHandler.class;
    CustomKeyMapping.initCategorySortOrder();
  }
}
