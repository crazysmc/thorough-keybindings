package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//$if <1.18
@Mixin(net.minecraft.client.gui.screens.controls.ControlList.KeyEntry.class)
//$if >=1.18
@Mixin(net.minecraft.client.gui.screens.controls.KeyBindsList.KeyEntry.class)
//$if
public abstract class KeyEntryMixin
{
  @Redirect(
      //$if <1.19.4
      method = "render",
      //$if >=1.19.4
      method = "refreshEntry",
      //$if
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;same(Lnet/minecraft/client/KeyMapping;)Z"))
  private boolean separateDebugCombos(KeyMapping instance, KeyMapping keyMapping)
  {
    return isDebug(instance) == isDebug(keyMapping) && instance.same(keyMapping);
  }

  @Unique
  private boolean isDebug(KeyMapping keyMapping)
  {
    return keyMapping.getName().contains(".debug.");
  }
}
