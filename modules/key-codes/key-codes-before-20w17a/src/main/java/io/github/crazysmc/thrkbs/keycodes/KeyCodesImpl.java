package io.github.crazysmc.thrkbs.keycodes;

import io.github.crazysmc.thrkbs.core.api.KeyCodes;

public class KeyCodesImpl implements KeyCodes
{
  @Override
  public int[] getDebugKeys()
  {
    return HardcodedMappingImpl.DEBUG_KEYS;
  }
}
