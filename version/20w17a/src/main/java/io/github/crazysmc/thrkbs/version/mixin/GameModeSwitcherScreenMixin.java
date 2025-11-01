package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.keyBinding;
import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MODE;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;

@SuppressWarnings("public-target")
@Mixin(targets = "net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen")
public abstract class GameModeSwitcherScreenMixin
{
  @Definition(
      id = "get",
      method = "Lnet/minecraft/client/resources/language/I18n;get(Ljava/lang/String;[Ljava/lang/Object;)" +
          "Ljava/lang/String;"
  )
  @Expression("get('debug.gamemodes.press_f4', ?)")
  @WrapOperation(method = "render", at = @At("MIXINEXTRAS:EXPRESSION"))
  private String render_pressF4(String key, Object[] args, Operation<String> original)
  {
    return keyBinding(REGISTRY.get(GAME_MODE).getTranslatedKeyText());
  }

  @WrapOperation(
      method = "checkToClose",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z")
  )
  private boolean checkToClose_isKeyDown(long window, int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isDown();
  }

  @Definition(id = "key", local = @Local(type = int.class, argsOnly = true, ordinal = 0))
  @Expression("key == @(293)")
  @ModifyExpressionValue(method = "keyPressed", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int keyPressed_intEqConst(int constant, int key, int scancode)
  {
    return REGISTRY.get(GAME_MODE).matches(key, scancode) ? key : GLFW_KEY_UNKNOWN;
  }
}
