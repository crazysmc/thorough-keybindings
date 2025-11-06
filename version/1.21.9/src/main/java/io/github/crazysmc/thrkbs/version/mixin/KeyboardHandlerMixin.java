package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import io.github.crazysmc.thrkbs.HardcodedMapping;
import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.EnumSet;

import static io.github.crazysmc.thrkbs.HardcodedMapping.PROFILER_0;
import static io.github.crazysmc.thrkbs.HardcodedMapping.PROFILER_9;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.*;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @Definition(id = "window", local = @Local(type = long.class, argsOnly = true))
  @Expression("window == ?")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPress_windowEq(boolean original, long window, int action, KeyEvent keyEvent)
  {
    if (original)
      KeyRemapping.setDown(InputConstants.getKey(keyEvent), action != GLFW_RELEASE);
    return original;
  }

  @WrapOperation(
      method = "keyPress",
      at = @At(
          value = "INVOKE",
          target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(Lcom/mojang/blaze3d/platform/Window;I)Z"
      )
  )
  private boolean keyPress_isKeyDown(Window window, int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isDown();
  }

  @Definition(id = "keyEvent", local = @Local(type = KeyEvent.class, argsOnly = true))
  @Definition(id = "key", method = "Lnet/minecraft/client/input/KeyEvent;key()I")
  @Expression("keyEvent.key() == @(?)")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int keyPress_intEqConst(int constant, long window, int action, KeyEvent keyEvent)
  {
    if (constant == GLFW_KEY_TAB || constant >= GLFW_KEY_RIGHT && constant <= GLFW_KEY_UP)
      return constant;
    KeyRemapping remapping = REGISTRY.getByDefault(constant);
    if (constant == GLFW_KEY_ESCAPE && remapping.isUnbound())
      return constant;
    return remapping.matches(keyEvent) ? keyEvent.key() : keyEvent.key() + 1;
  }

  @WrapOperation(
      method = "handleDebugKeys",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyEvent;key()I")
  )
  private int handleDebugKeys_key(KeyEvent instance, Operation<Integer> original)
  {
    for (KeyRemapping mapping : REGISTRY.getDebugKeys())
      if (mapping.matches(instance))
        return mapping.getDefaultKey().getValue();
    return GLFW_KEY_UNKNOWN;
  }

  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyEvent;getDigit()I")
  )
  private int keyPress_getDigit(KeyEvent instance, Operation<Integer> original)
  {
    for (HardcodedMapping mapping : EnumSet.range(PROFILER_0, PROFILER_9))
      if (REGISTRY.get(mapping).matches(instance))
        return mapping.ordinal() - PROFILER_0.ordinal();
    return -1;
  }
}
