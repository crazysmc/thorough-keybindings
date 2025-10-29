package io.github.crazysmc.thrkbs;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final Logger LOGGER = LogManager.getLogger();

  @Override
  public void onInitializeClient()
  {
    LOGGER.debug("client entrypoint");//TODO remove (?)
  }
}
