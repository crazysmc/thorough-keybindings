package io.github.crazysmc.thrkbs;

import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ThoroughKeybindings
{
  public static final String MOD_ID = "thorough-keybindings";
  public static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");

  private ThoroughKeybindings()
  {
    LOGGER.error("TEST {}", ModifyIntIfEqual.class);
  }
}
