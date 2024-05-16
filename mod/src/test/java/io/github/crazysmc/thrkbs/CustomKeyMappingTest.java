package io.github.crazysmc.thrkbs;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.mixin.KeyMappingAccessor;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomKeyMappingTest
{
  @Test
  void getKeyCodeByOriginalEmpty()
  {
    assertEquals(-42, CustomKeyMapping.getKeyCodeByOriginal(-42));
  }

  @Test
  void getKeyCodeByOriginalFound1()
  {
    try (MockedStatic<KeyMappingAccessor> accessor = mockStatic(KeyMappingAccessor.class))
    {
      accessor.when(KeyMappingAccessor::getAll).thenReturn(Collections.emptyMap());
      CustomKeyMapping key = mock(CustomKeyMapping.class, withSettings().useConstructor("test-key", -17, "test")
          .extraInterfaces(KeyMappingAccessor.class));
      accessor.when(((KeyMappingAccessor) key)::getKey).thenReturn(InputConstants.Type.KEYSYM.getOrCreate(-18));
      assertEquals(-18, CustomKeyMapping.getKeyCodeByOriginal(-17));
    }
  }
}
