package io.github.crazysmc.thrkbs.core;

import io.github.crazysmc.thrkbs.core.mixin.KeyMappingAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.KeyMapping;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static io.github.crazysmc.thrkbs.core.HardcodedMapping.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_3;

public class MappingRegistry
{
  private static final Set<HardcodedMapping> CHARTS_SET = EnumSet.of(CHARTS_PROFILER, CHARTS_FPS, CHARTS_NETWORK);

  private final Int2ObjectMap<HardcodedMapping> hardcodedMappings;
  private final Int2ObjectMap<KeyMapping> keyMappings = new Int2ObjectOpenHashMap<>();
  private final Set<HardcodedMapping> registeredMappings = EnumSet.noneOf(HardcodedMapping.class);

  private int debugCharts;

  public MappingRegistry()
  {
    HardcodedMapping[] mappings = values();
    hardcodedMappings = new Int2ObjectOpenHashMap<>(mappings.length);
    for (HardcodedMapping mapping : mappings)
      hardcodedMappings.put(mapping.getKeyCode(), mapping);
  }

  public Set<HardcodedMapping> getRegisteredMappings()
  {
    if (debugCharts > 0 && debugCharts < 3)
    {
      registeredMappings.removeAll(CHARTS_SET);
      debugCharts = 0;
    }
    return Collections.unmodifiableSet(registeredMappings);
  }

  public boolean registerKeyCode(int constant)
  {
    HardcodedMapping mapping = hardcodedMappings.get(constant);
    if (mapping == null)
      return false;
    if (constant >= GLFW_KEY_1 && constant <= GLFW_KEY_3 && !registeredMappings.contains(mapping))
      debugCharts++;
    return registeredMappings.add(mapping);
  }

  public void registerMapping(int defaultKey, KeyMapping mapping)
  {
    keyMappings.put(defaultKey, mapping);
  }

  public int remapKeyCode(int defaultKey)
  {
    KeyMapping mapping = keyMappings.get(defaultKey);
    return mapping == null ? defaultKey : ((KeyMappingAccessor) mapping).getKey().getValue();
  }

  public KeyMapping getMapping(int defaultKey)
  {
    return keyMappings.get(defaultKey);
  }
}
