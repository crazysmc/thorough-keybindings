package io.github.crazysmc.thrkbs.keycodes;

import io.github.crazysmc.thrkbs.core.api.KeyCodes;

import static org.lwjgl.input.Keyboard.*;

public class KeyCodesImpl implements KeyCodes
{
  @Override
  public int[] getDebugKeys()
  {
    return HardcodedMappingImpl.DEBUG_KEYS;
  }

  @Override
  public int getEscKeyCode()
  {
    return KEY_ESCAPE;
  }

  @Override
  public int getF3KeyCode()
  {
    return KEY_F3;
  }

  @Override
  public int getF4KeyCode()
  {
    return KEY_F4;
  }

  @Override
  public int getLetterKeyCode(char letter)
  {
    return getKeyIndex(String.valueOf(letter));
  }
}
