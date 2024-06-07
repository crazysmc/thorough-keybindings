package io.github.crazysmc.thrkbs.module;

import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import net.minecraft.client.KeyMapping;

public class SimpleKeyDisplay implements KeyDisplay
{
  @Override
  public String getDisplayName(KeyMapping mapping)
  {
    return mapping == null ? null : mapping.getTranslatedKeyMessage();
  }
}
