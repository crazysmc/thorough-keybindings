package io.github.crazysmc.thrkbs.remapkeys.mixin;

import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import net.minecraft.inventory.slot.InventorySlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;
import static org.lwjgl.input.Keyboard.KEY_1;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin
{
  @Shadow
  private InventorySlot hoveredSlot;

  @Shadow
  protected abstract void clickSlot(InventorySlot par1, int par2, int par3, int par4);

  @Inject(method = "moveHoveredSlotToHotbar", at = @At(value = "CONSTANT", args = "intValue=9"), cancellable = true)
  private void remapHotbarKeys(int keyCode, CallbackInfoReturnable<Boolean> cir)
  {
    for (int i = 0; i < 9; i++)
      if (keyCode == MAPPING_REGISTRY.remapKeyCode(KEY_1 + i))
      {
        clickSlot(hoveredSlot, hoveredSlot.id, i, 2);
        cir.setReturnValue(true);
      }
    cir.setReturnValue(false);
  }
}
