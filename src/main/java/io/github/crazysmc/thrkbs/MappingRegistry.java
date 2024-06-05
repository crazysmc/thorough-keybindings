package io.github.crazysmc.thrkbs;

import io.github.crazysmc.thrkbs.mixin.KeyMappingAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static io.github.crazysmc.thrkbs.HardcodedMapping.*;

public class MappingRegistry
{
  private static final Set<HardcodedMapping> CHARTS_SET = EnumSet.of(CHARTS_PROFILER, CHARTS_FPS, CHARTS_NETWORK);
  private final Int2ObjectMap<HardcodedMapping> hardcodedMappings = new Int2ObjectOpenHashMap<>();
  private final Int2ObjectMap<KeyMapping> keyMappings = new Int2ObjectOpenHashMap<>();
  private final Set<HardcodedMapping> registeredMappings = EnumSet.noneOf(HardcodedMapping.class);
  private int debugCharts;

  public MappingRegistry()
  {
    Arrays.stream(HardcodedMapping.values()).forEach(mapping -> hardcodedMappings.put(mapping.getKeyCode(), mapping));
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

  public void registerMapping(KeyMapping mapping)
  {
    keyMappings.put(mapping.getDefaultKey().getValue(), mapping);
  }

  public int remapKeyCode(int original)
  {
    KeyMapping mapping = keyMappings.get(original);
    return mapping == null ? original : ((KeyMappingAccessor) mapping).getKey().getValue();
  }
}
