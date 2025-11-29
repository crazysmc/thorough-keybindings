package io.github.crazysmc.thrkbs.version.mixintest.shared;

import io.github.crazysmc.thrkbs.version.mixin.KeyBindingAccessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyBindingAccessorTest
{
  @Test
  void getMap()
  {
    assertNotNull(KeyBindingAccessor.getMap());
  }
}
