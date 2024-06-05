package io.github.crazysmc.thrkbs;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.service.KeyDisplay;
import net.fabricmc.api.ClientModInitializer;

public class SimpleKeyDisplay implements KeyDisplay, ClientModInitializer
{
  @Override
  public String getDisplayName(int keyCode)
  {
    return InputConstants.translateKeyCode(keyCode);
  }

  @Override
  public void onInitializeClient()
  {
    KeyDisplayRegistry.setKeyDisplay(this);
  }
}
