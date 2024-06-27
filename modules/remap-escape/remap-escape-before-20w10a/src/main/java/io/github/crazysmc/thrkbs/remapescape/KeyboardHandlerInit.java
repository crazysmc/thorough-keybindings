package io.github.crazysmc.thrkbs.remapescape;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.KeyboardHandler;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.LOGGER;

public class KeyboardHandlerInit implements ClientModInitializer
{
  @Override
  public void onInitializeClient()
  {
    LOGGER.error("DEBUG Fabric init ran, do we need Ornithe entrypoints");
    @SuppressWarnings("unused") Class<?> forceLoad = KeyboardHandler.class;
  }
}
