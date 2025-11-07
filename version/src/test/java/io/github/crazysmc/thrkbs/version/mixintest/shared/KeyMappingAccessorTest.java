package io.github.crazysmc.thrkbs.version.mixintest.shared;

import io.github.crazysmc.thrkbs.version.mixin.shared.KeyMappingAccessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyMappingAccessorTest
{
  @Test
  void getMap()
  {
    assertNotNull(KeyMappingAccessor.getMap());
  }
}
