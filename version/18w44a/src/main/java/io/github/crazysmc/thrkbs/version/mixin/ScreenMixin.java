package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;

@Mixin(Screen.class)
public abstract class ScreenMixin
{
  @WrapOperation(
      method = { "isControlDown", "isShiftDown", "isAltDown" },
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;getKey(JI)Z"),
      require = 3
  )
  private static boolean hasModifierDown_getKey(long window, int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isPressed();
  }
}
