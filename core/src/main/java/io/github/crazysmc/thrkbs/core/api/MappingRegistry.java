package io.github.crazysmc.thrkbs.core.api;

import net.minecraft.client.options.KeyBinding;

import java.util.Set;

public interface MappingRegistry
{
  boolean registerKeyCode(int constant);

  Set<? extends HardcodedMapping> getRegisteredMappings();

  void registerMapping(int defaultKey, String name, KeyBinding mapping);

  int remapKeyCode(int defaultKey);

  KeyBinding getMapping(int defaultKey);
}
