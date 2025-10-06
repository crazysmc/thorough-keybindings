package io.github.crazysmc.thrkbs.mixintest;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.mixin.KeyMappingAccessor;
import net.minecraft.client.KeyMapping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class KeyMappingAccessorTest
{
  static Stream<Arguments> getKey()
  {
    return Stream.of(
        Arguments.of("key.test.1", 123, "key.categories.test"),
        Arguments.of("key.test.2", -45, "key.categories.test")
    );
  }

  @Test
  void getAll()
  {
    assertNotNull(KeyMappingAccessor.getAll());
  }

  @ParameterizedTest
  @MethodSource
  void getKey(String name, int keyCode, String category)
  {
    KeyMapping mapping = new KeyMapping(name, keyCode, category); // TODO will break with new category type
    InputConstants.Key key = ((KeyMappingAccessor) mapping).getKey();
    assertEquals(keyCode, key.getValue());
  }
}
