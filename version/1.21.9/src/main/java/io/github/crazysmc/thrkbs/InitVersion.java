package io.github.crazysmc.thrkbs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InitVersion implements ClientModInitializer
{
  private static final Logger LOGGER = LogManager.getLogger();

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
    for (MappingCategory category : MappingCategory.values())
      category.object = getCategory(category);
    for (HardcodedMapping mapping : HardcodedMapping.values())
      mapping.object = KeyBindingHelper.registerKeyBinding(getKeyMapping(mapping));
    LOGGER.debug("client entrypoint v1.21.9 ({})", ThoroughKeybindings.class.getName());
  }
}
