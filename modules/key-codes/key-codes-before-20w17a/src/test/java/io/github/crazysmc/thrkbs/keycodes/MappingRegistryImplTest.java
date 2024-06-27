package io.github.crazysmc.thrkbs.keycodes;

import io.github.crazysmc.thrkbs.core.api.MappingRegistry;
import org.junit.jupiter.api.Test;
import org.lwjgl.glfw.GLFW;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MappingRegistryImplTest
{

  @Test
  void getRegisteredMappingsEmpty()
  {
    assertTrue(new MappingRegistryImpl().getRegisteredMappings().isEmpty());
  }

  @Test
  void getRegisteredMappingsAfterRegisterKeyCode()
  {
    MappingRegistry mappingRegistry = new MappingRegistryImpl();
    assertTrue(mappingRegistry.registerKeyCode(GLFW.GLFW_KEY_F3));
    assertIterableEquals(Collections.singleton(HardcodedMappingImpl.DEBUG_INFO),
                         mappingRegistry.getRegisteredMappings());
  }
}
