package io.github.crazysmc.thrkbs.core;

import io.github.crazysmc.thrkbs.core.api.ChatComponents;
import io.github.crazysmc.thrkbs.core.api.KeyCodes;
import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import io.github.crazysmc.thrkbs.core.api.MappingRegistry;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");
  public static final ChatComponents CHAT_COMPONENTS = providerOrNull(ChatComponents.class);
  public static final KeyDisplay KEY_DISPLAY = providerOrNull(KeyDisplay.class);
  public static final KeyCodes KEY_CODES = providerOrNull(KeyCodes.class);
  public static final MappingRegistry MAPPING_REGISTRY = providerOrNull(MappingRegistry.class);
  public static final DynamicTextReplacer DYNAMIC_TEXT_REPLACER = new DynamicTextReplacer(MAPPING_REGISTRY,
                                                                                          KEY_DISPLAY);

  private static <T> T providerOrNull(Class<T> service)
  {
    Iterator<T> iterator = ServiceLoader.load(service).iterator();
    return iterator.hasNext() ? iterator.next() : null;
  }

  @Override
  public void onInitializeClient()
  {
    LOGGER.error("DEBUG Fabric init ran");
  }
}
