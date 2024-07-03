package io.github.crazysmc.thrkbs.keycodes;

import io.github.crazysmc.thrkbs.core.api.MappingRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.options.KeyBinding;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static io.github.crazysmc.thrkbs.keycodes.HardcodedMappingImpl.*;
import static org.lwjgl.input.Keyboard.KEY_1;

public class MappingRegistryImpl implements MappingRegistry
{
  private static final Set<HardcodedMappingImpl> HOTBAR_SET = EnumSet.range(HOTBAR_1, HOTBAR_9);

  private final Int2ObjectMap<HardcodedMappingImpl> hardcodedMappings;
  private final Int2ObjectMap<KeyBinding> keyMappings = new Int2ObjectOpenHashMap<>();
  private final Set<HardcodedMappingImpl> registeredMappings = EnumSet.noneOf(HardcodedMappingImpl.class);

  private boolean numberKeys;

  public MappingRegistryImpl()
  {
    HardcodedMappingImpl[] mappings = values();
    hardcodedMappings = new Int2ObjectOpenHashMap<>(mappings.length);
    for (HardcodedMappingImpl mapping : mappings)
      hardcodedMappings.put(mapping.getKeyCode(), mapping);
  }

  @Override
  public boolean registerKeyCode(int constant)
  {
    HardcodedMappingImpl mapping = hardcodedMappings.get(constant);
    if (mapping == null || constant == 9)
      return false;
    if (constant == KEY_1 && registeredMappings.contains(mapping))
      numberKeys = true;
    return registeredMappings.add(mapping);
  }

  @Override
  public Set<HardcodedMappingImpl> getRegisteredMappings()
  {
    if (numberKeys)
      registeredMappings.addAll(HOTBAR_SET);
    else
      registeredMappings.removeAll(HOTBAR_SET);
    return Collections.unmodifiableSet(registeredMappings);
  }

  @Override
  public void registerMapping(KeyBinding mapping)
  {
    keyMappings.put(mapping.getDefaultKeyCode(), mapping);
  }

  @Override
  public int remapKeyCode(int defaultKey)
  {
    KeyBinding mapping = keyMappings.get(defaultKey);
    return mapping == null ? defaultKey : mapping.getKeyCode();
  }

  @Override
  public KeyBinding getMapping(int defaultKey)
  {
    return keyMappings.get(defaultKey);
  }
}
