package io.github.crazysmc.thrkbs.keycodes;

import io.github.crazysmc.thrkbs.core.api.MappingRegistry;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static io.github.crazysmc.thrkbs.keycodes.HardcodedMappingImpl.DEBUG_INFO;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F3;

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
    assertTrue(mappingRegistry.registerKeyCode(GLFW_KEY_F3));
    assertIterableEquals(Collections.singleton(DEBUG_INFO), mappingRegistry.getRegisteredMappings());
  }
}
