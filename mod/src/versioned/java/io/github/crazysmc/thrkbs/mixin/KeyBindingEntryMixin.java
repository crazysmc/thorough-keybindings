//$if >=1.7
package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.gui.screen.options.ControlsListWidget;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ControlsListWidget.KeyBindingEntry.class)
public abstract class KeyBindingEntryMixin
{
  @Final
  @Shadow
  private KeyBinding keyBinding;

  @Unique
  private boolean isDebug(KeyBinding keyBinding)
  {
    return keyBinding.getName().contains(".debug.");
  }

  @Redirect(method = "render",
            slice = @Slice(from = @At(value = "FIELD",
                                      target = "Lnet/minecraft/client/options/GameOptions;keyBindings:[Lnet/minecraft/client/options/KeyBinding;")),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/KeyBinding;getKeyCode()I", ordinal = 0))
  private int separateDebugCombos(KeyBinding keyBinding)
  {
    return isDebug(keyBinding) != isDebug(this.keyBinding) ? 0 : keyBinding.getKeyCode();
  }
}
