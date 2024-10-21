package io.github.crazysmc.thrkbs.core.api;

public interface KeyCodes
{
  String DEBUG_CATEGORY = "key.categories.debug";
  String MODIFIER_CATEGORY = "key.categories.modifier";

  int[] getDebugKeys();

  int getEscKeyCode();

  int getF3KeyCode();

  int getF4KeyCode();

  int getLetterKeyCode(char letter);
}
