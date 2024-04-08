package io.github.crazysmc.thrkbs;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.mixin.KeyMappingAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.KeyMapping;

import java.util.Map;
import java.util.Optional;

public class CustomKeyMapping extends KeyMapping
{
  private static final Map<Integer, CustomKeyMapping> BY_ORIGINAL = new Int2ObjectOpenHashMap<>();

  public CustomKeyMapping(String name, int keyCode, String category)
  {
    super(name, keyCode, category);
    KeyMappingAccessor.getAll().remove(name);
    KeyMappingAccessor.getMap().remove(((KeyMappingAccessor) this).getKey());
    BY_ORIGINAL.put(keyCode, this);
  }

  public static void initCategorySortOrder()
  {
    Map<String, Integer> categorySortOrder = KeyMappingAccessor.getCategorySortOrder();
    int i = categorySortOrder.values().stream().mapToInt(Integer::intValue).max().orElse(0);
    categorySortOrder.put("key.categories.debug", i + 1);
    categorySortOrder.put("key.categories.modifier", i + 2);
  }

  public static int getKeyCodeByOriginal(int keyCode)
  {
    return Optional.ofNullable(BY_ORIGINAL.get(keyCode))
        .map(binding -> ((KeyMappingAccessor) binding).getKey())
        .map(InputConstants.Key::getValue)
        .orElse(keyCode);
  }

  public static String getDisplayNameByOriginal(int keyCode)
  {
    return Optional.ofNullable(BY_ORIGINAL.get(keyCode))
        .map(KeyMapping::getTranslatedKeyMessage)
        .orElse(InputConstants.translateKeyCode(keyCode));
  }
}
