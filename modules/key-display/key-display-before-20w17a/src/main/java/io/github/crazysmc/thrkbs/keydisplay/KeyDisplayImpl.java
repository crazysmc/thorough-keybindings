package io.github.crazysmc.thrkbs.keydisplay;

import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import io.github.crazysmc.thrkbs.keydisplay.mixin.KeyMappingAccessor;
import net.minecraft.client.options.KeyBinding;

public class KeyDisplayImpl implements KeyDisplay
{
  @Override
  public String getDisplayName(KeyBinding mapping)
  {
    return mapping == null ? null : mapping.getDisplayName();
  }

  @Override
  public int getKeyCode(KeyBinding mapping)
  {
    return ((KeyMappingAccessor) mapping).getKey().getValue();
  }

  @Override
  public int getDefaultKeyCode(KeyBinding mapping)
  {
    return mapping.getDefaultKey().getValue();
  }
}
