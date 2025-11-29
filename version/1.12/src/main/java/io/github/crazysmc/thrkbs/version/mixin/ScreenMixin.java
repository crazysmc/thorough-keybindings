package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.crazysmc.thrkbs.version.KeyRebinding;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;
import static org.lwjgl.input.Keyboard.KEY_ESCAPE;

@Mixin(Screen.class)
public abstract class ScreenMixin
{
  @WrapOperation(
      method = { "isControlDown", "isShiftDown", "isAltDown" },
      at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z"),
      require = 3
  )
  private static boolean isModifierDown_isKeyDown(int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isPressed();
  }

  @Definition(id = "key", local = @Local(type = int.class))
  @Expression("key == 1")
  @ModifyExpressionValue(method = "keyPressed", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPressed_keyEqEsc(boolean original, char chr, int key)
  {
    KeyRebinding rebinding = REGISTRY.getByDefault(KEY_ESCAPE);
    return rebinding.isUnbound() ? original : rebinding.matches(chr, key);
  }
}
