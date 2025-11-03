package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MODE;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin
{
  @WrapOperation(
      method = "checkToClose",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z")
  )
  private boolean checkToClose_isKeyDown(long window, int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isDown();
  }

  @Definition(id = "key", local = @Local(type = int.class, argsOnly = true, ordinal = 0))
  @Expression("key == 293")
  @ModifyExpressionValue(method = "keyPressed", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPressed_intEqConst(boolean original, int key, int scancode)
  {
    return REGISTRY.get(GAME_MODE).matches(key, scancode);
  }
}
