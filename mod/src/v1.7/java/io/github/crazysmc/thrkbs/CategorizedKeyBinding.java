package io.github.crazysmc.thrkbs;

import io.github.crazysmc.thrkbs.mixin.KeyBindingAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.options.KeyBinding;

import java.util.Map;
import java.util.Optional;

public class CategorizedKeyBinding extends KeyBinding
{
  private static final Map<Integer, CategorizedKeyBinding> BY_ORIGINAL = new Int2ObjectOpenHashMap<>();

  public CategorizedKeyBinding(String name, int keyCode, String category)
  {
    super(name, keyCode, category);
    KeyBindingAccessor.getAll().remove(this);
    BY_ORIGINAL.put(keyCode, this);
  }

  public static void initDefaultCategories()
  {
  }

  public static int getKeyCodeByOriginal(int keyCode)
  {
    return Optional.ofNullable(BY_ORIGINAL.get(keyCode)).map(KeyBinding::getKeyCode).orElse(keyCode);
  }
}
