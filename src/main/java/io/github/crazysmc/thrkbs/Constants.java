package io.github.crazysmc.thrkbs;

import java.util.Locale;

public interface Constants
{
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
