package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @WrapOperation(
      method = { "hasControlDown", "hasShiftDown", "hasAltDown" },
      at = @At(
          value = "INVOKE",
          target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(Lcom/mojang/blaze3d/platform/Window;I)Z"
      )
  )
  private static boolean hasModifierDown_isKeyDown(Window window, int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isDown();
  }
}
