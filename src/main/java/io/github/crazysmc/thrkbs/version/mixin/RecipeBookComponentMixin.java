package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.input.MouseButtonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.SHIFT_1;
import static io.github.crazysmc.thrkbs.HardcodedMapping.SHIFT_2;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(RecipeBookComponent.class)
public abstract class RecipeBookComponentMixin
{
  @WrapOperation(
      method = "mouseClicked",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/MouseButtonEvent;hasShiftDown()Z")
  )
  private boolean mouseClicked_hasShiftDown(MouseButtonEvent instance, Operation<Boolean> original)
  {
    return REGISTRY.get(SHIFT_1).isDown() || REGISTRY.get(SHIFT_2).isDown();
  }
}
