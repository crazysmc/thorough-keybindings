package io.github.crazysmc.thrkbs.mixin.screen;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import net.minecraft.inventory.slot.InventorySlot;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin extends Screen
{
  @Shadow
  private InventorySlot hoveredSlot;

  @Shadow
  protected abstract void clickSlot(InventorySlot invSlot, int slotId, int clickData, int actionType);

  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = Keyboard.KEY_ESCAPE))
  private int keyPressedRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }

  @Inject(method = "moveHoveredSlotToHotbar", at = @At("HEAD"), cancellable = true)
  protected void moveHoveredSlotToHotbar(int key, CallbackInfoReturnable<Boolean> cir)
  {
    if (minecraft.player.inventory.getCursorStack() == null && hoveredSlot != null)
      for (int i = 0; i < 9; ++i)
        if (key == ThoroughKeybindings.getHotbarRemap(i))
        {
          clickSlot(hoveredSlot, hoveredSlot.id, i, 2);
          cir.setReturnValue(true);
          return;
        }
    cir.setReturnValue(false);
  }
}
