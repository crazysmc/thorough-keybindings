package io.github.crazysmc.thrkbs;

public enum KeyCategory
{
  MOVEMENT("key.categories.movement"),
  GAMEPLAY("key.categories.gameplay"),
  INVENTORY("key.categories.inventory"),
  MULTIPLAYER("key.categories.multiplayer"),
  MISC("key.categories.misc"),
  DEBUG("key.categories.debug"),
  MODIFIER("key.categories.modifier"),
  PROFILER_CHART("key.categories.profilerChart"),
//  UI("key.categories.ui"),
  ;

  private final String name;

  KeyCategory(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }
}
