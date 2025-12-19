package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;isControlDown()Z")
  )
  private boolean keyPress_isControlDown(Operation<Boolean> original)
  {
    return REGISTRY.get(CTRL_1).isPressed() || REGISTRY.get(CTRL_2).isPressed();
  }

  @WrapOperation(
      method = { "keyPress", "handleDebugKeys" },
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;isShiftDown()Z")
  )
  private boolean keyPressDebugKeys_isShiftDown(Operation<Boolean> original)
  {
    return REGISTRY.get(SHIFT_1).isPressed() || REGISTRY.get(SHIFT_2).isPressed();
  }

  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;isAltDown()Z")
  )
  private boolean keyPress_isAltDown(Operation<Boolean> original)
  {
    return REGISTRY.get(ALT_1).isPressed() || REGISTRY.get(ALT_2).isPressed();
  }
}
