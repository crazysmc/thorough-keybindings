package io.github.crazysmc.thrkbs.remapkeys;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.KeyboardHandler;

public class KeyboardHandlerInit implements ClientModInitializer
{
  @Override
  public void onInitializeClient()
  {
    @SuppressWarnings("unused") Class<?> forceLoad = KeyboardHandler.class;
  }
}
