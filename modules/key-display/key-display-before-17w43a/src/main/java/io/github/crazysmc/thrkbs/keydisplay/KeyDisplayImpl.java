package io.github.crazysmc.thrkbs.keydisplay;

import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;

public class KeyDisplayImpl implements KeyDisplay
{
  @Override
  public String getDisplayName(KeyBinding mapping)
  {
    return mapping == null ? null : GameOptions.getKeyName(mapping.getKeyCode());
  }

  @Override
  public int getKeyCode(KeyBinding mapping)
  {
    return mapping.getKeyCode();
  }

  @Override
  public int getDefaultKeyCode(KeyBinding mapping)
  {
    return mapping.getDefaultKeyCode();
  }
}
