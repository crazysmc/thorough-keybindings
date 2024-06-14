package io.github.crazysmc.thrkbs.core;

import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import io.github.crazysmc.thrkbs.core.mixin.KeyMappingAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final MappingRegistry MAPPING_REGISTRY = new MappingRegistry();
  public static final KeyDisplay KEY_DISPLAY;
  public static final DynamicTextReplacer DYNAMIC_TEXT_REPLACER;
  private static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");

  static
  {
    Iterator<KeyDisplay> iterator = ServiceLoader.load(KeyDisplay.class).iterator();
    KEY_DISPLAY = iterator.hasNext() ? iterator.next() : null;
    DYNAMIC_TEXT_REPLACER = new DynamicTextReplacer(MAPPING_REGISTRY, KEY_DISPLAY);
  }

  @Override
  public void onInitializeClient()
  {
    for (HardcodedMapping mapping : MAPPING_REGISTRY.getRegisteredMappings())
    {
      String name = mapping.getName();
      LOGGER.info("Add keybinding {}", name);
      KeyMapping keyMapping = new KeyMapping(name, mapping.getKeyCode(), mapping.getCategory());
      KeyBindingHelper.registerKeyBinding(keyMapping);
      KeyMappingAccessor.getAll().remove(name);
      MAPPING_REGISTRY.registerMapping(keyMapping.getDefaultKey().getValue(), keyMapping);
    }
    KeyMapping.resetMapping();
  }
}
