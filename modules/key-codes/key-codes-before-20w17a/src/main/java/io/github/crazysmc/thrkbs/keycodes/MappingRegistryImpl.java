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
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_3;

public class MappingRegistryImpl implements MappingRegistry
{
  private static final Set<HardcodedMappingImpl> CHARTS_SET = EnumSet.of(CHARTS_PROFILER, CHARTS_FPS, CHARTS_NETWORK);

  private final Int2ObjectMap<HardcodedMappingImpl> hardcodedMappings;
  private final Int2ObjectMap<KeyBinding> keyMappings = new Int2ObjectOpenHashMap<>();
  private final Set<HardcodedMappingImpl> registeredMappings = EnumSet.noneOf(HardcodedMappingImpl.class);

  private int debugCharts;

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
    if (constant >= GLFW_KEY_1 && constant <= GLFW_KEY_3 && !registeredMappings.contains(mapping))
      debugCharts++;
    return registeredMappings.add(mapping);
  }

  @Override
  public Set<HardcodedMappingImpl> getRegisteredMappings()
  {
    if (debugCharts > 0 && debugCharts < 3)
    {
      registeredMappings.removeAll(CHARTS_SET);
      debugCharts = 0;
    }
    return Collections.unmodifiableSet(registeredMappings);
  }

  @Override
  public void registerMapping(KeyBinding mapping)
  {
    keyMappings.put(mapping.getDefaultKey().getValue(), mapping);
  }

  @Override
  public int remapKeyCode(int defaultKey)
  {
    KeyBinding mapping = keyMappings.get(defaultKey);
    return mapping == null ? defaultKey : ((KeyMappingAccessor) mapping).getKey().getValue();
  }

  @Override
  public KeyBinding getMapping(int defaultKey)
  {
    return keyMappings.get(defaultKey);
  }
}
