package io.github.crazysmc.thrkbs.core;

import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MappingRegistryTest
{
  @Test
  void getRegisteredMappingsEmpty()
  {
    assertTrue(new MappingRegistry(null).getRegisteredMappings().isEmpty());
  }

  @Test
  void getRegisteredMappingsAfterRegisterKeyCode()
  {
    MappingRegistry mappingRegistry = new MappingRegistry(null);
    assertTrue(mappingRegistry.registerKeyCode(GLFW.GLFW_KEY_F3));
    assertIterableEquals(Collections.singleton(HardcodedMapping.DEBUG_INFO), mappingRegistry.getRegisteredMappings());
  }
}
