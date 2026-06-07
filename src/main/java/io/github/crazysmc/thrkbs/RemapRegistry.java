package io.github.crazysmc.thrkbs;

import java.util.EnumMap;

public class RemapRegistry<T>
{
  private final EnumMap<HardcodedMapping, T> BY_MAPPING = new EnumMap<>(HardcodedMapping.class);

  public void register(HardcodedMapping mapping, T remapping)
  {
    BY_MAPPING.put(mapping, remapping);
  }

  public T get(HardcodedMapping mapping)
  {
    return BY_MAPPING.get(mapping);
  }

  public int size()
  {
    return BY_MAPPING.size();
  }
}
