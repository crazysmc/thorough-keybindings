package io.github.crazysmc.thrkbs.mixin.remap;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin
{
  @ModifyConstant(method = "moveHoveredSlotToHotbar",
                  constant = @Constant(intValue = Keyboard.KEY_1, ordinal = 0),
                  require = 0)
  private int remapHotbarKeyConstant(int constant, @Share("index") LocalIntRef index)
  {
    int i = index.get();
    index.set(i + 1);
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant + i) - i;
  }
}
