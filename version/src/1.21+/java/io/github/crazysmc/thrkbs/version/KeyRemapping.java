package io.github.crazysmc.thrkbs.version;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.HardcodedMapping;
import io.github.crazysmc.thrkbs.RemapRegistry;
import io.github.crazysmc.thrkbs.version.mixin.shared.KeyMappingAccessor;
import net.minecraft.client.KeyMapping;

import java.util.List;
import java.util.Optional;

public class KeyRemapping extends KeyMapping
{
  public static final RemapRegistry<KeyRemapping> REGISTRY = new RemapRegistry<>();

  private static final Categories CATEGORIES = new Categories();

  private boolean down;

  public KeyRemapping(HardcodedMapping mapping)
  {
    super(mapping.getId(), mapping.getKeyCode(), CATEGORIES.getCategory(mapping.getCategory().getType()));
    REGISTRY.register(mapping, this);
  }

  public static void setDown(InputConstants.Key key, boolean down)
  {
    List<KeyMapping> list = KeyMappingAccessor.getMap().get(key);
    if (list == null)
      return;
    for (KeyMapping mapping : list)
      if (mapping instanceof KeyRemapping)
        ((KeyRemapping) mapping).down = down;
  }

  public String getTranslatedKeyText()
  {
    return getTranslatedKeyMessage().visit(Optional::of).orElseThrow();
  }

  @Override
  public boolean isDown()
  {
    return down;
  }
}
