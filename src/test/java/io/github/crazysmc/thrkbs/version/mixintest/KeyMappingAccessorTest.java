package io.github.crazysmc.thrkbs.version.mixintest;

import io.github.crazysmc.thrkbs.version.mixin.KeyMappingAccessor;
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
