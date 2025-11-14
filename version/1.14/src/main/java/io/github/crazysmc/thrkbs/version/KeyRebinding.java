package io.github.crazysmc.thrkbs.version;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.HardcodedMapping;
import io.github.crazysmc.thrkbs.RemapRegistry;
import io.github.crazysmc.thrkbs.version.mixin.shared.KeyBindingAccessor;
import net.minecraft.client.options.KeyBinding;

import java.util.List;

public class KeyRebinding extends KeyBinding
{
  public static final RemapRegistry<KeyRebinding> REGISTRY = new RemapRegistry<>();

  private boolean pressed;

  public KeyRebinding(HardcodedMapping mapping)
  {
    super(mapping.getId(), mapping.getKeyCode(), mapping.getCategory().getId());
    REGISTRY.register(mapping, this);
  }

  public static void setPressed(InputConstants.Key key, boolean pressed)
  {
    List<KeyBinding> list = KeyBindingAccessor.getMap().get(key);
    if (list == null)
      return;
    for (KeyBinding binding : list)
      if (binding instanceof KeyRebinding)
        ((KeyRebinding) binding).pressed = pressed;
  }

  public String getTranslatedKeyText()
  {
    return getDisplayName();
  }

  @Override
  public boolean isPressed()
  {
    return pressed;
  }
}
