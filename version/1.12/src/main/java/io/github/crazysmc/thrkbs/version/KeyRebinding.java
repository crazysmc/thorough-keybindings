package io.github.crazysmc.thrkbs.version;

import io.github.crazysmc.thrkbs.HardcodedMapping;
import io.github.crazysmc.thrkbs.RemapRegistry;
import io.github.crazysmc.thrkbs.version.mixin.KeyBindingAccessor;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static org.lwjgl.input.Keyboard.KEY_NONE;

public class KeyRebinding extends KeyBinding
{
  public static final RemapRegistry<KeyRebinding> REGISTRY = new RemapRegistry<>();

  private boolean pressed;

  public KeyRebinding(HardcodedMapping mapping)
  {
    super(mapping.getId(), mapping.getKeyCode(), mapping.getCategory().getId());
    REGISTRY.register(mapping, this);
  }

  public static void setPressed(int keyCode, boolean pressed)
  {
    List<KeyBinding> list = KeyBindingAccessor.getMap().get(keyCode);
    if (list == null)
      return;
    for (KeyBinding binding : list)
      if (binding instanceof KeyRebinding)
        ((KeyRebinding) binding).pressed = pressed;
  }

  public String getTranslatedKeyText()
  {
    return GameOptions.getKeyName(getKeyCode());
  }

  @Override
  public boolean isPressed()
  {
    return pressed;
  }

  public boolean isUnbound()
  {
    return getKeyCode() != KEY_NONE;
  }

  public boolean matches(char chr, int key)
  {
    int keyCode = getKeyCode();
    return keyCode == key || keyCode == chr + Keyboard.KEYBOARD_SIZE;
  }
}
