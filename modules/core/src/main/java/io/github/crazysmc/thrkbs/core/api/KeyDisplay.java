package io.github.crazysmc.thrkbs.core.api;

import net.minecraft.client.KeyMapping;

import java.util.ServiceLoader;

public interface KeyDisplay
{
  static KeyDisplay getProvider()
  {
    return ServiceLoader.load(KeyDisplay.class).iterator().next();
  }

  String getDisplayName(KeyMapping mapping);
}
