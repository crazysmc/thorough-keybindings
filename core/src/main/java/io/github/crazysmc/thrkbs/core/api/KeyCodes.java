package io.github.crazysmc.thrkbs.core.api;

public interface KeyCodes
{
  String DEBUG_CATEGORY = "key.categories.debug";

  int[] getDebugKeys();

  int getEscKeyCode();

  int getF3KeyCode();

  int getF4KeyCode();

  int getLetterKeyCode(char letter);
}
