package io.github.crazysmc.thrkbs;

import org.lwjgl.input.Keyboard;

public enum KeyMapping
{
  ESCAPE(Keyboard.KEY_ESCAPE, "key.gameMenu", KeyCategory.MISC),
  F1(Keyboard.KEY_F1, "key.toggleHUD", KeyCategory.MISC),
  F2(Keyboard.KEY_F2, "key.screenshot", KeyCategory.MISC),
  F3(Keyboard.KEY_F3, "key.debugInfo", KeyCategory.MISC),
  ;

  private final int original;
  private final String name;
  private final KeyCategory category;

  KeyMapping(int original, String name, KeyCategory category)
  {
    this.original = original;
    this.name = name;
    this.category = category;
  }

  public int getOriginal()
  {
    return original;
  }

  public String getName()
  {
    return name;
  }

  public KeyCategory getCategory()
  {
    return category;
  }
}
