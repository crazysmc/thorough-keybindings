package io.github.crazysmc.thrkbs.core;

import io.github.crazysmc.thrkbs.core.mixin.KeyMappingAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static io.github.crazysmc.thrkbs.core.HardcodedMapping.*;

public class MappingRegistry
{
  private static final Set<HardcodedMapping> CHARTS_SET = EnumSet.of(CHARTS_PROFILER, CHARTS_FPS, CHARTS_NETWORK);
  private final Int2ObjectMap<HardcodedMapping> hardcodedMappings = new Int2ObjectOpenHashMap<>();
  private final Int2ObjectMap<KeyBinding> keyMappings = new Int2ObjectOpenHashMap<>();
  private final Set<HardcodedMapping> registeredMappings = EnumSet.noneOf(HardcodedMapping.class);
  private int debugCharts;

  public MappingRegistry()
  {
    Arrays.stream(HardcodedMapping.values())
        .unordered()
        .forEach(mapping -> hardcodedMappings.put(mapping.getKeyCode(), mapping));
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
    if (constant >= GLFW.GLFW_KEY_1 && constant <= GLFW.GLFW_KEY_3 && !registeredMappings.contains(mapping))
      debugCharts++;
    return registeredMappings.add(mapping);
  }

  public void registerMapping(int defaultKey, KeyBinding mapping)
  {
    keyMappings.put(defaultKey, mapping);
  }

  public int remapKeyCode(int defaultKey)
  {
    KeyBinding mapping = keyMappings.get(defaultKey);
    return mapping == null ? defaultKey : ((KeyMappingAccessor) mapping).getKey().getValue();
  }

  public KeyBinding getMapping(int defaultKey)
  {
    return keyMappings.get(defaultKey);
  }
}
