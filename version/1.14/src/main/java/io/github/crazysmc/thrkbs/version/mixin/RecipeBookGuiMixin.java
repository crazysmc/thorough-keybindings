package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.SHIFT_1;
import static io.github.crazysmc.thrkbs.HardcodedMapping.SHIFT_2;
import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;

@Mixin(RecipeBookGui.class)
public abstract class RecipeBookGuiMixin
{
  @WrapOperation(
      method = "mouseClicked",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;hasShiftDown()Z")
  )
  private boolean mouseClicked_hasShiftDown(Operation<Boolean> original)
  {
    return REGISTRY.get(SHIFT_1).isPressed() || REGISTRY.get(SHIFT_2).isPressed();
  }
}
