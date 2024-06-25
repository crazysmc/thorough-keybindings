package io.github.crazysmc.thrkbs.keydisplay;

import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import net.minecraft.client.options.KeyBinding;

public class KeyDisplayImpl implements KeyDisplay
{
  @Override
  public String getDisplayName(KeyBinding mapping)
  {
    return mapping == null ? null : mapping.getDisplayName();
  }
}
