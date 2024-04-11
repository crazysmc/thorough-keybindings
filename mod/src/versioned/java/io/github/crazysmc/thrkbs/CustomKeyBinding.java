package io.github.crazysmc.thrkbs;

import io.github.crazysmc.thrkbs.mixin.KeyBindingAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.util.Map;
import java.util.Optional;

public class CustomKeyBinding extends KeyBinding
{
  private static final Map<Integer, CustomKeyBinding> BY_ORIGINAL = new Int2ObjectOpenHashMap<>();

  public CustomKeyBinding(String name, int keyCode, String category)
  {
    super(name, keyCode, category);
    KeyBindingAccessor.getAll().remove(name);
    BY_ORIGINAL.put(keyCode, this);
  }

  public static void initCategorySortOrder()
  {
    Map<String, Integer> categorySortOrder = KeyBindingAccessor.getCategorySortOrder();
    int i = categorySortOrder.values().stream().mapToInt(Integer::intValue).max().orElse(0);
    categorySortOrder.put("key.categories.debug", i + 1);
    categorySortOrder.put("key.categories.modifier", i + 2);
  }

  public static int getKeyCodeByOriginal(int keyCode)
  {
    return Optional.ofNullable(BY_ORIGINAL.get(keyCode))
        .map(binding -> ((KeyBindingAccessor) binding).getKey())
        .map(InputUtil.KeyCode::getKeyCode)
        .orElse(keyCode);
  }

  public static String getDisplayNameByOriginal(int keyCode)
  {
    return Optional.ofNullable(BY_ORIGINAL.get(keyCode)).map(KeyBinding::getLocalizedName)
        //$if <1.16
        .orElse(InputUtil.getKeycodeName(keyCode))
        //$if >=1.16
        //$ .orElse(InputConstants.Type.KEYSYM.getOrCreate(keyCode).getDisplayName()).getString()
        //$if
        ;
  }

}
