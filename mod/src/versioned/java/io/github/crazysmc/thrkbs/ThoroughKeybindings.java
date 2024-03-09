package io.github.crazysmc.thrkbs;

import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import net.ornithemc.osl.keybinds.api.KeyBindingEvents;
import net.ornithemc.osl.keybinds.api.KeyBindingRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final String MOD_ID = "thorough-keybindings";
  public static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");

  @Override
  public void initClient()
  {
    CategorizedKeyBinding.initDefaultCategories();
    KeyBindingEvents.REGISTER_KEYBINDS.register(this::register);
    Class<?>[] forceLoad = {
        //$if >=1.13
        net.minecraft.client.KeyboardHandler.class,
        //$if
    };
  }

  private void register(KeyBindingRegistry registry)
  {
    PotentialKeyBinding.forEach(binding -> {
      String name = binding.getName();
      if (binding.isFound())
      {
        LOGGER.info("Add keybinding {}", name);
        registry.register(new CategorizedKeyBinding(name, binding.getKeyCode(), binding.getCategory()));
      }
      else
        LOGGER.debug("{} was not added", name);
    });
  }
}
