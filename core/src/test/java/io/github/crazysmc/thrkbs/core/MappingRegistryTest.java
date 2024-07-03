package io.github.crazysmc.thrkbs.core;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static io.github.crazysmc.thrkbs.core.HardcodedMapping.DEBUG_INFO;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F3;

class MappingRegistryTest
{
  @Test
  void getRegisteredMappingsEmpty()
  {
    assertTrue(new MappingRegistry().getRegisteredMappings().isEmpty());
  }

  @Test
  void getRegisteredMappingsAfterRegisterKeyCode()
  {
    MappingRegistry mappingRegistry = new MappingRegistry();
    assertTrue(mappingRegistry.registerKeyCode(GLFW_KEY_F3));
    assertIterableEquals(Collections.singleton(DEBUG_INFO), mappingRegistry.getRegisteredMappings());
  }
}
