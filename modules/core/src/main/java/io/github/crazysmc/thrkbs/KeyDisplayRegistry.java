package io.github.crazysmc.thrkbs;

import io.github.crazysmc.thrkbs.service.KeyDisplay;

import java.util.Objects;

public class KeyDisplayRegistry
{
  private static KeyDisplay keyDisplay;

  public static KeyDisplay getKeyDisplay()
  {
    return Objects.requireNonNull(keyDisplay);
  }

  public static void setKeyDisplay(KeyDisplay keyDisplay)
  {
    KeyDisplayRegistry.keyDisplay = keyDisplay;
  }
}
