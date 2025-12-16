package io.github.crazysmc.thrkbs.version;

import net.minecraft.client.KeyMapping.Category;
import net.minecraft.resources.Identifier;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static io.github.crazysmc.thrkbs.ThoroughKeybindings.LOGGER;
import static java.lang.reflect.AccessFlag.STATIC;

public class Categories
{
  private final Map<String, Category> map;

  public Categories()
  {
    Field[] declaredFields = Category.class.getDeclaredFields();
    map = new HashMap<>(declaredFields.length);
    for (Field field : declaredFields)
      if (field.accessFlags().contains(STATIC) && field.canAccess(null))
        try
        {
          if (field.get(null) instanceof Category category)
            map.put(category.id().getPath(), category);
        }
        catch (IllegalAccessException e)
        {
          LOGGER.error("could not access key mapping categories", e);
        }
  }

  public Category getCategory(String path)
  {
    return map.computeIfAbsent(path, string -> Category.register(Identifier.withDefaultNamespace(string)));
  }
}
