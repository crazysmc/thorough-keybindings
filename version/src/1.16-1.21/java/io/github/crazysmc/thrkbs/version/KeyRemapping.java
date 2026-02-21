package io.github.crazysmc.thrkbs.version;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.HardcodedMapping;
import io.github.crazysmc.thrkbs.RemapRegistry;
import net.minecraft.client.KeyMapping;

import java.util.*;

import static io.github.crazysmc.thrkbs.ThoroughKeybindings.MULTIMAP;

public class KeyRemapping extends KeyMapping
{
  public static final RemapRegistry<KeyRemapping> REGISTRY = new RemapRegistry<>();

  private boolean down;

  public KeyRemapping(HardcodedMapping mapping)
  {
    super(mapping.getId(), mapping.getKeyCode(), mapping.getCategory().getId());
    REGISTRY.register(mapping, this);
  }

  public static void setDown(InputConstants.Key key, boolean down)
  {
    List<KeyMapping> list = MULTIMAP.get(key);
    if (list == null)
      return;
    for (KeyMapping mapping : list)
      if (mapping instanceof KeyRemapping)
        ((KeyRemapping) mapping).down = down;
  }

  public String getTranslatedKeyText()
  {
    return getTranslatedKeyMessage().visit(Optional::of).orElseThrow(NoSuchElementException::new);
  }

  @Override
  public boolean isDown()
  {
    return down;
  }
}
