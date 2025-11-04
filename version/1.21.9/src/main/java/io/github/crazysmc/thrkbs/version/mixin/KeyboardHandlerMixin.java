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
import io.github.crazysmc.thrkbs.version.shared.RemappedTranslatableContents;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.EnumSet;

import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.*;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @Definition(id = "window", local = @Local(type = long.class, argsOnly = true))
  @Expression("window == ?")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPress_longEq(boolean original, long window, int action, KeyEvent keyEvent)
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
    if (remapping.matches(keyEvent))
      return keyEvent.key();
    if (constant == GLFW_KEY_ESCAPE && remapping.isUnbound())
      return GLFW_KEY_ESCAPE; /* make sure we can open the game menu */
    return GLFW_KEY_UNKNOWN;
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

  @WrapOperation(
      method = "handleDebugKeys",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)" +
              "Lnet/minecraft/network/chat/MutableComponent;"
      )
  )
  private MutableComponent handleDebugKeys_translatable(String key, Operation<MutableComponent> original)
  {
    return MutableComponent.create(new RemappedTranslatableContents(REGISTRY.getByDebugHelp(key), key));
  }

  @WrapOperation(
      method = "debugFeedbackTranslated",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)" +
              "Lnet/minecraft/network/chat/MutableComponent;"
      )
  )
  private MutableComponent debugFeedbackTranslated_translatable(String key, Operation<MutableComponent> original)
  {
    return "debug.crash.message".equals(key)
        ? MutableComponent.create(new RemappedTranslatableContents(REGISTRY.get(COPY_LOCATION), key))
        : original.call(key);
  }
}
