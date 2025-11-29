package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.crazysmc.thrkbs.version.KeyRebinding;
import net.minecraft.client.gui.screen.options.ControlsListWidget.KeyBindingEntry;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyBindingEntry.class)
public abstract class KeyBindingEntryMixin
{
  @Shadow
  @Final
  private KeyBinding keyBinding;

  @Definition(id = "getKeyCode", method = "Lnet/minecraft/client/options/KeyBinding;getKeyCode()I")
  @Expression("?.getKeyCode() == ?.getKeyCode()")
  @ModifyExpressionValue(method = "render", at = @At("MIXINEXTRAS:EXPRESSION"))
  boolean render_getKeyCodeEq(boolean original, @Local KeyBinding other)
  {
    return original && keyBinding instanceof KeyRebinding == other instanceof KeyRebinding;
  }
}
