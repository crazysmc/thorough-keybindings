package io.github.crazysmc.thrkbs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Keyboard;
import net.minecraft.client.options.KeyBinding;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThoroughKeybindings implements ClientModInitializer
{
  public static final String MOD_ID = "thorough-keybindings";
  public static final Logger LOGGER = LogManager.getLogger("Thorough Keybindings");

  @Override
  public void onInitializeClient()
  {
    CustomKeyBinding.initCategorySortOrder();
    Class<?> forceLoad = Keyboard.class;
    registerKeyBindings();
  }

  private void registerKeyBindings()
  {
    PotentialKeyBinding.getFoundBindings().forEach(binding -> {
      String name = binding.getName();
      LOGGER.info("Add keybinding {}", name);
      KeyBindingHelper.registerKeyBinding(new CustomKeyBinding(name, binding.getKeyCode(), binding.getCategory()));
    });
    KeyBinding.updateKeysByCode();
  }
}
