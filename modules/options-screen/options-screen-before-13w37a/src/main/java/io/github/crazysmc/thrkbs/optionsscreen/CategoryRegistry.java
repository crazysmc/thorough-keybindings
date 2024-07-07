package io.github.crazysmc.thrkbs.optionsscreen;

import io.github.crazysmc.thrkbs.core.api.HardcodedMapping;

import java.util.*;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

public class CategoryRegistry
{
  private final Set<String> categories = new LinkedHashSet<>();
  private final Map<String, String> categoryMap = new HashMap<>();

  public CategoryRegistry()
  {
    categories.add("key.categories.movement");
    categories.add("key.categories.gameplay");
    categories.add("key.categories.inventory");
    categories.add("key.categories.multiplayer");
    categories.add("key.categories.misc");
    categoryMap.put("key.attack", "key.categories.gameplay");
    categoryMap.put("key.back", "key.categories.movement");
    categoryMap.put("key.chat", "key.categories.multiplayer");
    categoryMap.put("key.command", "key.categories.multiplayer");
    categoryMap.put("key.drop", "key.categories.inventory");
    categoryMap.put("key.forward", "key.categories.movement");
    categoryMap.put("key.inventory", "key.categories.inventory");
    categoryMap.put("key.jump", "key.categories.movement");
    categoryMap.put("key.left", "key.categories.movement");
    categoryMap.put("key.pickItem", "key.categories.gameplay");
    categoryMap.put("key.playerlist", "key.categories.multiplayer");
    categoryMap.put("key.right", "key.categories.movement");
    categoryMap.put("key.sneak", "key.categories.movement");
    categoryMap.put("key.use", "key.categories.gameplay");
    categoryMap.put("key.fog", "key.categories.gameplay");
    for (HardcodedMapping mapping : MAPPING_REGISTRY.getRegisteredMappings())
    {
      String category = mapping.getCategory();
      categories.add(category);
      categoryMap.put(mapping.getName(), category);
    }
  }

  public List<String> getCategories()
  {
    return new ArrayList<>(categories);
  }

  public String getCategory(String name)
  {
    return categoryMap.getOrDefault(name, "key.categories.misc");
  }
}
