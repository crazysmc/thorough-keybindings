package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.CTRL_1;
import static io.github.crazysmc.thrkbs.HardcodedMapping.CTRL_2;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @WrapOperation(
      method = { "handleKeybinds", "pickBlockOrEntity" },
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;hasControlDown()Z")
  )
  private boolean handleKeybindsPickBlock_hasControlDown(Minecraft instance, Operation<Boolean> original)
  {
    return REGISTRY.get(CTRL_1).isDown() || REGISTRY.get(CTRL_2).isDown();
  }
}
