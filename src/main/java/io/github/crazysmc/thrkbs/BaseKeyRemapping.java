package io.github.crazysmc.thrkbs;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;

public abstract class BaseKeyRemapping
{
  private static final int CAPACITY = HardcodedMapping.values().length;
  private static final EnumMap<HardcodedMapping, BaseKeyRemapping> BY_MAPPING = new EnumMap<>(HardcodedMapping.class);
  private static final Int2ObjectMap<BaseKeyRemapping> BY_DEFAULT_KEY_CODE = new Int2ObjectOpenHashMap<>(CAPACITY);
  private static final Map<String, BaseKeyRemapping> BY_DEBUG_HELP = new HashMap<>(CAPACITY);

  public BaseKeyRemapping(HardcodedMapping mapping)
  {
    BY_MAPPING.put(mapping, this);
    BY_DEFAULT_KEY_CODE.put(mapping.getKeyCode(), this);

    String debugHelp = mapping.getDebugHelpId();
    if (debugHelp != null)
      BY_DEBUG_HELP.put(debugHelp, this);
  }

  public static BaseKeyRemapping get(HardcodedMapping mapping)
  {
    return BY_MAPPING.get(mapping);
  }

  public static BaseKeyRemapping getByDefault(int keyCode)
  {
    return BY_DEFAULT_KEY_CODE.get(keyCode);
  }

  public static BaseKeyRemapping getByDebugHelp(String debugHelp)
  {
    return BY_DEBUG_HELP.get(debugHelp);
  }

  public static Collection<BaseKeyRemapping> getDebugKeys()
  {
    return unmodifiableCollection(BY_DEBUG_HELP.values());
  }
}
