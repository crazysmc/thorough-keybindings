package io.github.crazysmc.thrkbs.keycodes;

import io.github.crazysmc.thrkbs.core.api.MappingRegistry;
import io.github.crazysmc.thrkbs.keycodes.mixin.KeyMappingAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.options.KeyBinding;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static io.github.crazysmc.thrkbs.keycodes.HardcodedMappingImpl.*;

public class MappingRegistryImpl implements MappingRegistry
{
  private static final Set<HardcodedMappingImpl> HOTBAR_SET = EnumSet.range(HOTBAR_1, HOTBAR_9);

  private final Int2ObjectMap<HardcodedMappingImpl> hardcodedMappings;
  private final Int2ObjectMap<KeyBinding> keyMappings = new Int2ObjectOpenHashMap<>();
  private final Set<HardcodedMappingImpl> registeredMappings = EnumSet.noneOf(HardcodedMappingImpl.class);

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
    if (mapping == null)
      return false;
    return registeredMappings.add(mapping);
  }

  @Override
  public Set<HardcodedMappingImpl> getRegisteredMappings()
  {
    /* rebinding of hotbar hotkeys was added at the same time as the togglePerspective key, 13w37a */
    if (registeredMappings.contains(TOGGLE_PERSPECTIVE))
      registeredMappings.addAll(HOTBAR_SET);
    else
      registeredMappings.removeAll(HOTBAR_SET);
    return Collections.unmodifiableSet(registeredMappings);
  }

  @Override
  public void registerMapping(int defaultKey, KeyBinding mapping)
  {
    keyMappings.put(defaultKey, mapping);
  }

  @Override
  public int remapKeyCode(int defaultKey)
  {
    KeyBinding mapping = keyMappings.get(defaultKey);
    return mapping == null ? defaultKey : ((KeyMappingAccessor) mapping).getKeyCode();
  }

  @Override
  public KeyBinding getMapping(int defaultKey)
  {
    return keyMappings.get(defaultKey);
  }
}
