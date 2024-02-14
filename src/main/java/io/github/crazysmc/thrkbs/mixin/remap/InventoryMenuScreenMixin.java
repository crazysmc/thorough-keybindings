package io.github.crazysmc.thrkbs.mixin.remap;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin
{
  @ModifyArg(method = "mouseClicked",
             at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z", remap = false))
  private int remapKeyDownArgument(int key)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(key);
  }

  @ModifyConstant(method = "moveHoveredSlotToHotbar", constant = @Constant(intValue = Keyboard.KEY_1, ordinal = 0))
  private int remapHotbarKeyConstant(int constant, @Local(ordinal = 1) int i)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant + i) - i;
  }
}
