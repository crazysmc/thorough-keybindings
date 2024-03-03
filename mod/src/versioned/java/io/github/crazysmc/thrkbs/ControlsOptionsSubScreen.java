//$if <1.7
package io.github.crazysmc.thrkbs;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;

import java.util.List;

public class ControlsOptionsSubScreen extends ControlsOptionsScreen
{
  private final String titleKey;
  private final List<KeyBinding> keyBindings;

  public ControlsOptionsSubScreen(Screen parent, GameOptions options, String titleKey, List<KeyBinding> keyBindings)
  {
    super(parent, options);
    this.titleKey = titleKey;
    this.keyBindings = keyBindings;
  }

  public String getTitleKey()
  {
    return titleKey;
  }

  public List<KeyBinding> getKeyBindings()
  {
    return keyBindings;
  }
}
