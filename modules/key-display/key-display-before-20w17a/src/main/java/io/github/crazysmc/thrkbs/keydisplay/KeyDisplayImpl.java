package io.github.crazysmc.thrkbs.keydisplay;

import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import net.minecraft.client.KeyMapping;

public class KeyDisplayImpl implements KeyDisplay
{
  @Override
  public String getDisplayName(KeyMapping mapping)
  {
    return mapping == null ? null : mapping.getTranslatedKeyMessage();
  }
}
