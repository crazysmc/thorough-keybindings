package io.github.crazysmc.thrkbs.version;

import io.github.crazysmc.thrkbs.HardcodedMapping;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

import static io.github.crazysmc.thrkbs.ThoroughKeybindings.LOGGER;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

public class Initializer implements ClientModInitializer
{
  @Override
  public void onInitializeClient()
  {
    HardcodedMapping.getExisting().forEach(mapping -> KeyMappingHelper.registerKeyMapping(new KeyRemapping(mapping)));
    LOGGER.debug("registered {} keybindings", REGISTRY::size);
  }
}
