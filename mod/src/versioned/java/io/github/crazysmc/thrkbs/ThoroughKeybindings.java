package io.github.crazysmc.thrkbs;

import net.minecraft.client.options.KeyBinding;
import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final String MOD_ID = "thorough-keybindings";
  public static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");

  @Override
  public void initClient()
  {
    CustomKeyBinding.initDefaultCategories();
    //$if >=1.3.0
    net.ornithemc.osl.keybinds.api.KeyBindingEvents.REGISTER_KEYBINDS.register(this::register);
    //$if >=1.13.0
    Class<?> forceLoad = net.minecraft.client.KeyboardHandler.class;
    //$if
  }

  //$if >=1.3.0
  private void register(net.ornithemc.osl.keybinds.api.KeyBindingRegistry registry)
  {
    PotentialKeyBinding.getFoundBindings().forEach(binding -> {
      String name = binding.getName();
      LOGGER.info("Add keybinding {}", name);
      registry.register(new CustomKeyBinding(name, binding.getKeyCode(), binding.getCategory()));
    });
    KeyBinding.resetMapping();
  }
  //$if
}
