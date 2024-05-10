package io.github.crazysmc.thrkbs;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.mixin.KeyMappingAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.KeyMapping;

import java.util.Map;

public class CustomKeyMapping extends KeyMapping
{
  private static final Map<Integer, CustomKeyMapping> BY_ORIGINAL = new Int2ObjectOpenHashMap<>();

  public CustomKeyMapping(String name, int keyCode, String category)
  {
    super(name, keyCode, category);
    KeyMappingAccessor.getAll().remove(name);
    BY_ORIGINAL.put(keyCode, this);
  }

  public static int getKeyCodeByOriginal(int keyCode)
  {
    KeyMapping keyMapping = BY_ORIGINAL.get(keyCode);
    if (keyMapping == null)
      return keyCode;
    return ((KeyMappingAccessor) keyMapping).getKey().getValue();
  }

  public static String getDisplayNameByOriginal(int keyCode)
  {
    KeyMapping keyMapping = BY_ORIGINAL.get(keyCode);
    if (keyMapping == null)
    {
      //$if <1.16
      return InputConstants.translateKeyCode(keyCode);
      //$if >=1.16
      return InputConstants.Type.KEYSYM.getOrCreate(keyCode).getDisplayName().getString();
      //$if
    }
    return keyMapping.getTranslatedKeyMessage()
        //$if >=1.16
        .getString()
        //$if
        ;
  }
}
