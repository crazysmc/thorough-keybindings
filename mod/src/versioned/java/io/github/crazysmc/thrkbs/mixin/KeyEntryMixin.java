package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.controls.ControlList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ControlList.KeyEntry.class)
public abstract class KeyEntryMixin
{
  @Redirect(method = "render",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/KeyMapping;same(Lnet/minecraft/client/KeyMapping;)Z"))
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
