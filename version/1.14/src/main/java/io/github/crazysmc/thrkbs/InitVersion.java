package io.github.crazysmc.thrkbs;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InitVersion implements ClientModInitializer
{
  private static final Logger LOGGER = LogManager.getLogger();

  @Override
  public void onInitializeClient()
  {
    LOGGER.debug("client entrypoint v1.14 ({})", ThoroughKeybindings.class.getName());
  }
}
