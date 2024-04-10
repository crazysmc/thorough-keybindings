package io.github.crazysmc.thrkbs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThoroughKeybindingsTest
{
  @Test
  void initClient()
  {
    Assertions.assertThrows(AssertionError.class, () -> new ThoroughKeybindings().onInitializeClient());
  }
}
