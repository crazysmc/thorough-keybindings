package io.github.crazysmc.thrkbs.version.shared;

import io.github.crazysmc.thrkbs.HardcodedMapping;
import io.github.crazysmc.thrkbs.version.KeyRebinding;
import net.fabricmc.api.ClientModInitializer;
import net.ornithemc.osl.keybinds.api.KeyBindingRegistry;

import static io.github.crazysmc.thrkbs.ThoroughKeybindings.LOGGER;
import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;
import static net.ornithemc.osl.keybinds.api.KeyBindingEvents.REGISTER_KEYBINDS;

public class Initializer implements ClientModInitializer
{
  @Override
  public void onInitializeClient()
  {
    REGISTER_KEYBINDS.register(this::registerKeybindings);
  }

  private void registerKeybindings(KeyBindingRegistry registry)
  {
    HardcodedMapping.getExisting().forEach(mapping -> registry.register(new KeyRebinding(mapping)));
    LOGGER.debug("registered {} keybindings", REGISTRY::size);
  }
}
