package io.github.crazysmc.thrkbs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.github.crazysmc.thrkbs.Constants.INIT_DEBUG_MSG;

public class InitVersion implements ClientModInitializer
{
  private static final Logger LOGGER = LogManager.getLogger();
  private static final MappingCategory[] KNOWN_CATEGORIES = MappingCategory.values();
  private static final HardcodedMapping[] KNOWN_MAPPINGS = HardcodedMapping.values();

  private static KeyMapping.Category getCategory(MappingCategory category)
  {
    return switch (category)
    {
      case MISC -> KeyMapping.Category.MISC;
      default -> KeyMapping.Category.register(ResourceLocation.withDefaultNamespace(category.getType()));
    };
  }

  private static KeyMapping getKeyMapping(HardcodedMapping mapping)
  {
    return new KeyMapping(mapping.getName(), mapping.getKeyCode(), (KeyMapping.Category) mapping.getCategory().object);
  }

  @Override
  public void onInitializeClient()
  {
    for (MappingCategory category : KNOWN_CATEGORIES)
      category.object = getCategory(category);
    for (HardcodedMapping mapping : KNOWN_MAPPINGS)
      mapping.object = KeyBindingHelper.registerKeyBinding(getKeyMapping(mapping));
    LOGGER.debug(INIT_DEBUG_MSG, KNOWN_MAPPINGS.length);
  }
}
