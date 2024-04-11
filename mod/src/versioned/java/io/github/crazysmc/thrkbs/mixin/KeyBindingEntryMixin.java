package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.gui.screen.options.ControlsListWidget;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ControlsListWidget.KeyBindingEntry.class)
public abstract class KeyBindingEntryMixin
{
  @Redirect(method = "render",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/options/KeyBinding;equals(Lnet/minecraft/client/options/KeyBinding;)Z"))
  private boolean separateDebugCombos(KeyBinding instance, KeyBinding keyBinding)
  {
    return isDebug(instance) == isDebug(keyBinding) && instance.equals(keyBinding);
  }

  @Unique
  private boolean isDebug(KeyBinding keyBinding)
  {
    return keyBinding.getName().contains(".debug.");
  }
}
