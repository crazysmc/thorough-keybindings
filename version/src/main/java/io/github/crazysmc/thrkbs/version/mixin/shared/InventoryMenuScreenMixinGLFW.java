package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;

import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixinGLFW
{
  @Group(name = "getKey")
  @WrapOperation(
      method = { "mouseClicked", "mouseReleased" },
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;getKey(JI)Z")
  )
  private boolean mouseAction_getKey(long window, int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isPressed();
  }

  @Group(name = "getKey")
  @WrapOperation(
      method = { "mouseClicked", "mouseReleased" },
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;getKey(I)Z")
  )
  private boolean mouseAction_getKey(int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isPressed();
  }
}
