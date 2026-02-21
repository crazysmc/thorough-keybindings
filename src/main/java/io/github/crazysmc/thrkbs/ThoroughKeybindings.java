package io.github.crazysmc.thrkbs;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.minecraft.client.KeyMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static io.github.crazysmc.thrkbs.Versions.V25W41A;

public interface ThoroughKeybindings
{
  Logger LOGGER = LogManager.getLogger();

  Version MC_VERSION = FabricLoader.getInstance()
      .getModContainer("minecraft")
      .orElseThrow(() -> new IllegalStateException("could not find minecraft"))
      .getMetadata()
      .getVersion();

  /**
   * same as Minecraft.ON_OSX
   */
  boolean ON_OSX = System.getProperty("os.name").toLowerCase(Locale.ROOT).contains("mac");

  /**
   * most bindings are only needed before 25w41a
   */
  boolean NO_DEBUG_BINDS = MC_VERSION.compareTo(V25W41A) < 0;

  /**
   * key mapping multimap before 25w36a
   */
  Map<InputConstants.Key, List<KeyMapping>> MULTIMAP = new HashMap<>();
}
