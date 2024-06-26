//$if >=1.4.0-alpha.12.40.a <1.7.0
package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CustomKeyBinding;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin
{
  @Unique
  private int index;

  @Inject(method = "moveHoveredSlotToHotbar", at = @At("HEAD"))
  private void initIndex(CallbackInfoReturnable<Boolean> cir)
  {
    index = 0;
  }

  @ModifyConstant(method = "moveHoveredSlotToHotbar", constant = @Constant(intValue = Keyboard.KEY_1, ordinal = 0))
  private int remapHotbarKeyConstant(int constant)
  {
    int i = index++;
    return CustomKeyBinding.getKeyCodeByOriginal(constant + i) - i;
  }
}
