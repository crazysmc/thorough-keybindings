package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MENU;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.*;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @Definition(id = "window", local = @Local(type = long.class, argsOnly = true))
  @Expression("window == ?")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPress_longEq(boolean original, long window, int key, int scancode, int action)
  {
    if (original)
      KeyRemapping.setDown(InputConstants.getKey(key, scancode), action != GLFW_RELEASE);
    return original;
  }

  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z")
  )
  private boolean keyPress_isKeyDown(long window, int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isDown();
  }

  @Definition(id = "key", local = @Local(type = int.class, argsOnly = true, ordinal = 0))
  @Expression("key == @(?)")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int keyPress_intEqConst(int constant, long window, int key, int scancode)
  {
    return REGISTRY.getByDefault(constant).matches(key, scancode) ? key : GLFW_KEY_UNKNOWN;
  }

  @ModifyArg(
      method = "lambda$keyPress$4",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/gui/components/events/ContainerEventHandler;keyPressed(III)Z"
      ),
      index = 0
  )
  private int keyPress_lambda_keyPressed(int key, int scancode, int mods)
  {
    return REGISTRY.get(GAME_MENU).matches(key, scancode) ? GLFW_KEY_ESCAPE : key;
  }

  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyboardHandler;handleDebugKeys(I)Z")
  )
  private boolean keyPress_handleDebugKeys(KeyboardHandler instance, int key, Operation<Boolean> original,
                                           long window, int _key, int scancode)
  {
    for (KeyRemapping mapping : REGISTRY.getDebugKeys())
      if (mapping.matches(key, scancode))
        return original.call(instance, mapping.getDefaultKey().getValue());
    return original.call(instance, GLFW_KEY_UNKNOWN);
  }
}
