package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin
{
  @WrapOperation(
      method = { "mouseClicked", "mouseReleased" },
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/input/MouseButtonEvent;hasShiftDown()Z"
      )
  )
  private boolean mouseAction_hasShiftDown(MouseButtonEvent instance, Operation<Boolean> original)
  {
    return REGISTRY.get(SHIFT_1).isDown() || REGISTRY.get(SHIFT_2).isDown();
  }

  @WrapOperation(
      method = "keyPressed",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/input/KeyEvent;hasControlDown()Z"
      )
  )
  private boolean keyPressed_hasControlDown(KeyEvent instance, Operation<Boolean> original)
  {
    return REGISTRY.get(CTRL_1).isDown() || REGISTRY.get(CTRL_2).isDown();
  }
}
