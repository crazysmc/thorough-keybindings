package io.github.crazysmc.thrkbs.optionsscreen.mixin;

import net.minecraft.client.gui.screen.options.ControlsListWidget;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import static io.github.crazysmc.thrkbs.core.api.KeyCodes.DEBUG_CATEGORY;
import static io.github.crazysmc.thrkbs.core.api.KeyCodes.MODIFIER_CATEGORY;

@Mixin(ControlsListWidget.KeyBindingEntry.class)
public abstract class KeyBindingEntryMixin
{
  @Shadow
  @Final
  private KeyBinding keyBinding;

  @Redirect(method = "render",
            slice = @Slice(from = @At(value = "FIELD",
                                      target = "Lnet/minecraft/client/options/GameOptions;keyBindings:[Lnet/minecraft/client/options/KeyBinding;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/KeyBinding;getKeyCode()I", ordinal = 0))
  private int separateDebugCombos(KeyBinding keyBinding)
  {
    String thisCategory = this.keyBinding.getCategory();
    String thatCategory = keyBinding.getCategory();
    return DEBUG_CATEGORY.equals(thisCategory) != DEBUG_CATEGORY.equals(thatCategory) ||
        MODIFIER_CATEGORY.equals(thisCategory) != MODIFIER_CATEGORY.equals(thatCategory) ? 0 : keyBinding.getKeyCode();
  }
}
