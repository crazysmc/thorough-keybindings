package io.github.crazysmc.thrkbs;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;

public class RemapRegistry<T>
{
  private final int CAPACITY = HardcodedMapping.values().length;
  private final EnumMap<HardcodedMapping, T> BY_MAPPING = new EnumMap<>(HardcodedMapping.class);
  private final Int2ObjectMap<T> BY_DEFAULT_KEY_CODE = new Int2ObjectOpenHashMap<>(CAPACITY);
  private final Map<String, T> BY_DEBUG_HELP = new HashMap<>(CAPACITY);

  public void register(HardcodedMapping mapping, T remapping)
  {
    BY_MAPPING.put(mapping, remapping);
    BY_DEFAULT_KEY_CODE.put(mapping.getKeyCode(), remapping);

    String debugHelp = mapping.getDebugHelpId();
    if (debugHelp != null)
      BY_DEBUG_HELP.put(debugHelp, remapping);
  }

  public T get(HardcodedMapping mapping)
  {
    return BY_MAPPING.get(mapping);
  }

  public int size()
  {
    return BY_MAPPING.size();
  }

  public T getByDefault(int keyCode)
  {
    return BY_DEFAULT_KEY_CODE.get(keyCode);
  }

  public T getByDebugHelp(String debugHelp)
  {
    return BY_DEBUG_HELP.get(debugHelp);
  }

  public Collection<T> getDebugKeys()
  {
    return unmodifiableCollection(BY_DEBUG_HELP.values());
  }
}
