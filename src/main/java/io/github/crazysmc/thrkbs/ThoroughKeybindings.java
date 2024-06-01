package io.github.crazysmc.thrkbs;

import io.github.crazysmc.thrkbs.mixin.KeyMappingAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThoroughKeybindings implements ClientModInitializer
{
  private static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");
  private static final MappingRegistry MAPPING_REGISTRY = new MappingRegistry();

  public static MappingRegistry getMappingRegistry()
  {
    return MAPPING_REGISTRY;
  }

  @Override
  public void onInitializeClient()
  {
    for (HardcodedMapping mapping : MAPPING_REGISTRY.getRegisteredMappings())
    {
      String name = mapping.getName();
      LOGGER.debug("Add keybinding {}", name);
      KeyBindingHelper.registerKeyBinding(new KeyMapping(name, mapping.getKeyCode(), mapping.getCategory()));
      KeyMappingAccessor.getAll().remove(name);
    }
    KeyMapping.resetMapping();
  }
}
