package io.github.crazysmc.thrkbs.version.mixin;

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
    return REGISTRY.get(GAME_MENU).matches((KeyEvent) (Object) this);
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
