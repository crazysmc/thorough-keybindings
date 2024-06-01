package io.github.crazysmc.thrkbs;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.lwjgl.glfw.GLFW;

import java.util.*;

import static io.github.crazysmc.thrkbs.HardcodedMapping.*;

public class MappingRegistry
{
  private static final Set<HardcodedMapping> CHARTS_SET = EnumSet.of(CHARTS_PROFILER, CHARTS_FPS, CHARTS_NETWORK);
  private final Int2ObjectMap<HardcodedMapping> allMappings = new Int2ObjectOpenHashMap<>();
  private final Set<HardcodedMapping> registeredMappings = EnumSet.noneOf(HardcodedMapping.class);
  private int debugCharts;

  public MappingRegistry()
  {
    Arrays.stream(HardcodedMapping.values()).forEach(mapping -> allMappings.put(mapping.getKeyCode(), mapping));
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
    HardcodedMapping mapping = allMappings.get(constant);
    if (mapping == null)
      return false;
    if (constant >= GLFW.GLFW_KEY_1 && constant <= GLFW.GLFW_KEY_3 && !registeredMappings.contains(mapping))
      debugCharts++;
    return registeredMappings.add(mapping);
  }
}
