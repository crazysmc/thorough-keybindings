package io.github.crazysmc.thrkbs;

import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final String MOD_ID = "thorough-keybindings";
  public static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");

  @Override
  public void initClient()
  {
    CustomKeyBinding.initDefaultCategories();
    //$if >=1.13.0
    Class<?> forceLoad = net.minecraft.client.KeyboardHandler.class;
    //$if
  }
}
