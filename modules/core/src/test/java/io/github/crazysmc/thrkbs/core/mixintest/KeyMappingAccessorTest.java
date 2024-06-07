package io.github.crazysmc.thrkbs.core.mixintest;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.core.mixin.KeyMappingAccessor;
import net.minecraft.client.KeyMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class KeyMappingAccessorTest
{
  private final int keyCode = new Random().nextInt();

  @Test
  void getAll()
  {
    assertNotNull(KeyMappingAccessor.getAll());
  }

  @Test
  void getKey()
  {
    KeyMapping mapping = new KeyMapping("key.test", keyCode, "key.categories.test");
    InputConstants.Key key = ((KeyMappingAccessor) mapping).getKey();
    Assertions.assertEquals(keyCode, key.getValue());
  }
}
