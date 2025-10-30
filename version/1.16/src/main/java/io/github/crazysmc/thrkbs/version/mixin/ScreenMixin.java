package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Screen.class)
public abstract class ScreenMixin
{
  @WrapOperation(
      method = { "hasControlDown", "hasShiftDown", "hasAltDown" },
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z")
  )
  private static boolean hasModifierDown_isKeyDown(long window, int constant, Operation<Boolean> original)
  {
    return KeyRemapping.getByDefault(constant).isDown();
  }
}
