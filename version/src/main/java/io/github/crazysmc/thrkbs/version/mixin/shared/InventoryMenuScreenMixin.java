package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin
{
  @WrapOperation(
      method = { "mouseClicked", "mouseReleased" },
      at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z")
  )
  private boolean mouseAction_isKeyDown(int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isPressed();
  }
}
