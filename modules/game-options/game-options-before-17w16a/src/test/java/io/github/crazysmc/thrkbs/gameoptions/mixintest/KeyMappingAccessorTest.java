package io.github.crazysmc.thrkbs.gameoptions.mixintest;

import io.github.crazysmc.thrkbs.gameoptions.mixin.KeyMappingAccessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class KeyMappingAccessorTest
{
  @Test
  void getAll()
  {
    assertNotNull(KeyMappingAccessor.getAll());
  }
}
