package io.github.crazysmc.thrkbs.version.mixin;

import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;

import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(KeyEvent.class)
public abstract class KeyEventMixin implements InputWithModifiers
{
  @Override
  public boolean isEscape()
  {
    KeyRemapping remapping = REGISTRY.get(GAME_MENU); /* make sure we can open the game menu */
    return remapping.isUnbound() ? InputWithModifiers.super.isEscape() : remapping.matches((KeyEvent) (Object) this);
  }

  @Override
  public boolean hasAltDown()
  {
    return REGISTRY.get(ALT_1).isDown() || REGISTRY.get(ALT_2).isDown();
  }

  @Override
  public boolean hasShiftDown()
  {
    return REGISTRY.get(SHIFT_1).isDown() || REGISTRY.get(SHIFT_2).isDown();
  }

  @Override
  public boolean hasControlDown()
  {
    return REGISTRY.get(CTRL_1).isDown() || REGISTRY.get(CTRL_2).isDown();
  }
}
