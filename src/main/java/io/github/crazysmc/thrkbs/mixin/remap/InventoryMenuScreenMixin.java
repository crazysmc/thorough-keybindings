package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryMenuScreenMixin
{
  @ModifyArg(method = "mouseClicked",
             at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z", remap = false))
  private int remapKeyDownArgument(int key)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(key);
  }
}
