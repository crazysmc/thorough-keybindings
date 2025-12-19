package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin
{
  @WrapOperation(
      method = { "mouseClicked", "mouseReleased" },
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;getKey(I)Z")
  )
  private boolean mouseAction_getKey(int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isPressed();
  }

  @WrapOperation(
      method = "mouseReleased",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/gui/screen/inventory/menu/InventoryMenuScreen;isShiftDown()Z"
      )
  )
  private boolean mouseReleased_isShiftDown(Operation<Boolean> original)
  {
    return REGISTRY.get(SHIFT_1).isPressed() || REGISTRY.get(SHIFT_2).isPressed();
  }

  @WrapOperation(
      method = "m_6815938",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/gui/screen/inventory/menu/InventoryMenuScreen;isControlDown()Z"
      )
  )
  private boolean keyPressed_isControlDown(Operation<Boolean> original)
  {
    return REGISTRY.get(CTRL_1).isPressed() || REGISTRY.get(CTRL_2).isPressed();
  }
}
