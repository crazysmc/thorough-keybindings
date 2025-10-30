package io.github.crazysmc.thrkbs.version;

import io.github.crazysmc.thrkbs.HardcodedMapping;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.KeyMapping;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;

public class KeyRemapping extends KeyMapping
{
  private static final int CAPACITY = HardcodedMapping.values().length;
  private static final EnumMap<HardcodedMapping, KeyRemapping> BY_MAPPING = new EnumMap<>(HardcodedMapping.class);
  private static final Int2ObjectMap<KeyRemapping> BY_DEFAULT_KEY_CODE = new Int2ObjectOpenHashMap<>(CAPACITY);
  private static final Map<String, KeyRemapping> BY_DEBUG_HELP = new HashMap<>(CAPACITY);

  public KeyRemapping(HardcodedMapping mapping)
  {
    super(mapping.getId(), mapping.getKeyCode(), mapping.getCategory().getId());
    BY_MAPPING.put(mapping, this);
    BY_DEFAULT_KEY_CODE.put(mapping.getKeyCode(), this);
    String debugHelp = mapping.getDebugHelpId();
    if (debugHelp != null)
      BY_DEBUG_HELP.put(debugHelp, this);
  }

  public static KeyRemapping get(HardcodedMapping mapping)
  {
    return BY_MAPPING.get(mapping);
  }

  public static KeyRemapping getByDefault(int keyCode)
  {
    return BY_DEFAULT_KEY_CODE.get(keyCode);
  }

  public static KeyRemapping getByDebugHelp(String debugHelp)
  {
    return BY_DEBUG_HELP.get(debugHelp);
  }

  public static Collection<KeyRemapping> getDebugKeys()
  {
    return unmodifiableCollection(BY_DEBUG_HELP.values());
  }
}
