package io.github.crazysmc.thrkbs;

import net.minecraft.client.options.KeyBinding;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ThoroughKeybindings implements ClientModInitializer
{
  //  public static final String MOD_ID = "thorough-keybindings";
  public static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");
  private static final Map<KeyBinding, KeyMapping> MAPPINGS = new HashMap<>();
  private static final Properties DEFAULT_CATEGORIES = new Properties();

  public static void putMapping(KeyBinding keyBinding, KeyMapping keyMapping)
  {
    MAPPINGS.put(keyBinding, keyMapping);
    LOGGER.log(Level.INFO, "Add keybinding for {} as {}", Keyboard.getKeyName(keyMapping.getOriginal()),
               keyMapping.getName());
  }

  public static KeyMapping getMapping(KeyBinding keyBinding)
  {
    return keyBinding == null ? null : MAPPINGS.get(keyBinding);
  }

  public static KeyCategory getCategory(KeyBinding keyBinding)
  {
    KeyMapping mapping = getMapping(keyBinding);
    if (mapping != null)
      return mapping.getCategory();
    String category = DEFAULT_CATEGORIES.getProperty(keyBinding.name);
    if (category != null)
      try
      {
        return KeyCategory.valueOf(category);
      }
      catch (IllegalArgumentException e)
      {
        LOGGER.error("Invalid category key", e);
      }
    return KeyCategory.MISC;
  }

  @Override
  public void initClient()
  {
    try
    {
      DEFAULT_CATEGORIES.load(
          ThoroughKeybindings.class.getResourceAsStream("/assets/thorough-keybindings/key.categories.properties"));
    }
    catch (NullPointerException | IllegalArgumentException | IOException e)
    {
      LOGGER.error("Could not read key category asset", e);
    }
  }
}
