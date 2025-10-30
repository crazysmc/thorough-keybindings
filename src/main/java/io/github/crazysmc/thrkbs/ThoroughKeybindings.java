package io.github.crazysmc.thrkbs;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public interface ThoroughKeybindings
{
  Logger LOGGER = LogManager.getLogger();

  Version MC_VERSION = FabricLoader.getInstance()
      .getModContainer("minecraft")
      .orElseThrow(() -> new IllegalStateException("could not find minecraft"))
      .getMetadata()
      .getVersion();

  String INIT_DEBUG_MSG = "registered {} keybindings";

  /**
   * same as Minecraft.ON_OSX
   */
  boolean ON_OSX = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac");

  /**
   * number of key mapping categories from minecraft and this mod
   */
  int CATEGORY_SIZE = 8;
}
