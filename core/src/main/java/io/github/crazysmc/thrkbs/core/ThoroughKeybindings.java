package io.github.crazysmc.thrkbs.core;

import io.github.crazysmc.thrkbs.core.api.ChatComponents;
import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import io.github.crazysmc.thrkbs.core.mixin.KeyMappingAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.ServiceLoader;

import static io.github.crazysmc.thrkbs.core.MappingRegistry.MAPPING_REGISTRY;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final KeyDisplay KEY_DISPLAY = providerOrNull(KeyDisplay.class);
  public static final ChatComponents CHAT_COMPONENTS = providerOrNull(ChatComponents.class);
  public static final DynamicTextReplacer DYNAMIC_TEXT_REPLACER =
      new DynamicTextReplacer(MAPPING_REGISTRY, KEY_DISPLAY);
  private static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");

  private static <T> T providerOrNull(Class<T> service)
  {
    Iterator<T> iterator = ServiceLoader.load(service).iterator();
    return iterator.hasNext() ? iterator.next() : null;
  }

  @Override
  public void onInitializeClient()
  {
    @SuppressWarnings("unused") Class<?> forceLoad = KeyboardHandler.class;
    for (HardcodedMapping mapping : MAPPING_REGISTRY.getRegisteredMappings())
    {
      String name = mapping.getName();
      LOGGER.debug("Add keybinding {}", name);
      KeyMapping keyMapping = new KeyMapping(name, mapping.getKeyCode(), mapping.getCategory());
      KeyBindingHelper.registerKeyBinding(keyMapping);
      KeyMappingAccessor.getAll().remove(name);
      MAPPING_REGISTRY.registerMapping(keyMapping.getDefaultKey().getValue(), keyMapping);
    }
    KeyMapping.resetMapping();
  }
}
