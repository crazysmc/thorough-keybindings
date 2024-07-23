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
    return DEBUG_CATEGORY.equals(keyBinding.getCategory()) !=
        DEBUG_CATEGORY.equals(this.keyBinding.getCategory()) ? 0 : keyBinding.getKeyCode();
  }
}
