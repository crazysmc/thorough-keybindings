package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = Keyboard.KEY_ESCAPE))
  private int remapEscapeKey(int constant)
  {
    return CategorizedKeyBinding.getByOriginal(constant).keyCode;
  }

  @ModifyConstant(method = "mouseClicked", constant = {
      @Constant(intValue = Keyboard.KEY_LSHIFT), @Constant(intValue = Keyboard.KEY_RSHIFT)
  })
  private int remapShiftKeys(int constant)
  {
    return CategorizedKeyBinding.getByOriginal(constant).keyCode;
  }
}
