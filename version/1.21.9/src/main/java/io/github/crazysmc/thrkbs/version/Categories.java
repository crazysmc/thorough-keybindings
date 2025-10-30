package io.github.crazysmc.thrkbs.version;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static io.github.crazysmc.thrkbs.ThoroughKeybindings.CATEGORY_SIZE;
import static io.github.crazysmc.thrkbs.ThoroughKeybindings.LOGGER;
import static java.lang.reflect.AccessFlag.STATIC;

public class Categories
{
  private final Map<String, KeyMapping.Category> map = new HashMap<>(CATEGORY_SIZE);

  public Categories()
  {
    for (Field field : KeyMapping.Category.class.getDeclaredFields())
      if (field.accessFlags().contains(STATIC) && field.canAccess(null))
        try
        {
          if (field.get(null) instanceof KeyMapping.Category category)
            map.put(category.id().getPath(), category);
        }
        catch (IllegalAccessException e)
        {
          LOGGER.error("could not access key mapping categories", e);
        }
  }

  public KeyMapping.Category getCategory(String path)
  {
    return map.computeIfAbsent(path, string -> KeyMapping.Category.register(
        ResourceLocation.withDefaultNamespace(string)));
  }
}
