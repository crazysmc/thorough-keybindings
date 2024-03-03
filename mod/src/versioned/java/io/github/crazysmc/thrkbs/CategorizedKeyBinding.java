package io.github.crazysmc.thrkbs;

import io.github.crazysmc.thrkbs.mixin.KeyBindingAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.options.KeyBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static io.github.crazysmc.thrkbs.ThoroughKeybindings.LOGGER;

public class CategorizedKeyBinding extends KeyBinding
{
  //$if <1.7
  private static final Set<String> CATEGORIES = new TreeSet<>();
  private static final Properties DEFAULT_CATEGORIES = new Properties();

  private final String category;
  //$if
  private static final Map<Integer, CategorizedKeyBinding> BY_ORIGINAL = new Int2ObjectOpenHashMap<>();

  public CategorizedKeyBinding(String name, int keyCode, String category)
  {
    //$if <1.7
    super(name, keyCode);
    this.category = category;
    CATEGORIES.add(category);
    //$if >=1.7
    super(name, keyCode, category);
    //$if
    KeyBindingAccessor.getAll().remove(this);
    BY_ORIGINAL.put(keyCode, this);
  }

  public static void initDefaultCategories()
  {
    //$if <1.7
    String path = String.format("/assets/%s/keys/category.properties", ThoroughKeybindings.MOD_ID);
    try (InputStream stream = CategorizedKeyBinding.class.getResourceAsStream(path))
    {
      DEFAULT_CATEGORIES.load(stream);
      DEFAULT_CATEGORIES.values().forEach(object -> CATEGORIES.add(((String) object)));
    }
    catch (IOException | IllegalArgumentException | NullPointerException e)
    {
      LOGGER.error("Could not read key category asset", e);
    }
    //$if
  }

  //$if <1.7
  public static List<String> getCategories()
  {
    return new ArrayList<>(CATEGORIES);
  }

  public static String getCategory(KeyBinding keyBinding)
  {
    if (keyBinding instanceof CategorizedKeyBinding)
      return ((CategorizedKeyBinding) keyBinding).category;
    return DEFAULT_CATEGORIES.getProperty(keyBinding.name, "key.categories.misc");
  }
  //$if

  public static int getKeyCodeByOriginal(int keyCode)
  {
    //$if <1.7
    return Optional.ofNullable(BY_ORIGINAL.get(keyCode)).map(binding -> binding.keyCode).orElse(keyCode);
    //$if >=1.7
    return Optional.ofNullable(BY_ORIGINAL.get(keyCode)).map(KeyBinding::getKeyCode).orElse(keyCode);
    //$if
  }
}
