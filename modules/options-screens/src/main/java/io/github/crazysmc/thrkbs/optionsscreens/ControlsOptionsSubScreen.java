package io.github.crazysmc.thrkbs.optionsscreens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;

import java.util.List;

public class ControlsOptionsSubScreen extends ControlsOptionsScreen
{
  private final String category;
  private final List<KeyBinding> keyMappings;

  public ControlsOptionsSubScreen(Screen parent, GameOptions options, String category, List<KeyBinding> keyMappings)
  {
    super(parent, options);
    this.category = category;
    this.keyMappings = keyMappings;
  }

  public String getCategory()
  {
    return category;
  }

  public List<KeyBinding> getKeyMappings()
  {
    return keyMappings;
  }
}
