package io.github.crazysmc.thrkbs.core.api;

import net.minecraft.client.options.KeyBinding;

public interface KeyDisplay
{
  String getDisplayName(KeyBinding mapping);

  int getKeyCode(KeyBinding mapping);

  int getDefaultKeyCode(KeyBinding mapping);
}
