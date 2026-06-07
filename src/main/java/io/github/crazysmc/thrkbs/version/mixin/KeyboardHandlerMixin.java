package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.HardcodedMapping;
import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.EnumSet;

import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

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

  @Definition(id = "keyEvent", local = @Local(type = KeyEvent.class, argsOnly = true))
  @Definition(id = "key", method = "Lnet/minecraft/client/input/KeyEvent;key()I")
  @Expression("keyEvent.key() == 66")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPress_keyEqB(boolean original, long window, int action, KeyEvent keyEvent)
  {
    return REGISTRY.get(NARRATOR).matches(keyEvent);
  }

  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyEvent;hasControlDownWithQuirk()Z")
  )
  private boolean keyPress_hasControlDownWithQuirk(KeyEvent instance, Operation<Boolean> original)
  {
    return REGISTRY.get(CTRL_1).isDown() || REGISTRY.get(CTRL_2).isDown();
  }

  @WrapOperation(
      method = "tick",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;hasControlDown()Z")
  )
  private boolean tick_hasControlDown(Minecraft instance, Operation<Boolean> original)
  {
    return REGISTRY.get(CTRL_1).isDown() || REGISTRY.get(CTRL_2).isDown();
  }

  @WrapOperation(
      method = "handleDebugKeys",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyEvent;hasShiftDown()Z")
  )
  private boolean handleDebugKeys_hasShiftDown(KeyEvent instance, Operation<Boolean> original)
  {
    return REGISTRY.get(SHIFT_1).isDown() || REGISTRY.get(SHIFT_2).isDown();
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
