package io.github.crazysmc.thrkbs;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.crazysmc.thrkbs.Versions.V263S4;
import static io.github.crazysmc.thrkbs.Versions.V263S6;

public interface ThoroughKeybindings
{
  Logger LOGGER = LogManager.getLogger();

  Version MC_VERSION = FabricLoader.getInstance()
      .getModContainer("minecraft")
      .orElseThrow(() -> new IllegalStateException("could not find minecraft"))
      .getMetadata()
      .getVersion();

  /**
   * GLFW before 26.3-snapshot-4 was replaced by SDL3
   */
  boolean HAS_GLFW = MC_VERSION.compareTo(V263S4) < 0;

  /**
   * profiler bindings are only provided before 26.3-snapshot-6
   */
  boolean HAS_GETDIGIT = MC_VERSION.compareTo(V263S6) < 0;
}
