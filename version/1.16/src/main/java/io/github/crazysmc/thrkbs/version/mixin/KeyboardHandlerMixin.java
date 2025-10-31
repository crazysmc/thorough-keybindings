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
import io.github.crazysmc.thrkbs.version.RemappedTranslatableComponent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;

import static com.mojang.blaze3d.platform.InputConstants.getKey;
import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.*;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @Unique
  private InputConstants.Key addedKey;

  @Definition(id = "window", local = @Local(type = long.class, argsOnly = true))
  @Expression("window == ?")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPress_longEq(boolean original, long window, int key, int scancode, int action, int mods)
  {
    if (original)
    {
      addedKey = getKey(key, scancode);
      KeyMapping.set(addedKey, action != GLFW_RELEASE);
    }
    return original;
  }

  @Inject(
      method = "keyPress",
      at = @At(
          value = "INVOKE",
          target = "Lcom/mojang/blaze3d/platform/InputConstants;getKey(II)" +
              "Lcom/mojang/blaze3d/platform/InputConstants$Key;"
      )
  )
  private void keyPress_getKey(CallbackInfo ci)
  {
    addedKey = null;
  }

  @Inject(method = "keyPress", at = @At("RETURN"))
  private void keyPress_return(CallbackInfo ci)
  {
    if (addedKey != null)
      KeyMapping.set(addedKey, false); /* prevent inventory walk */
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

  @Definition(id = "key", local = @Local(type = int.class, argsOnly = true, ordinal = 0))
  @Expression("@(key) >= '0'")
  @ModifyVariable(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"), argsOnly = true, ordinal = 0)
  private int keyPress_intDigit(int key, long window, int _key, int scancode)
  {
    for (HardcodedMapping mapping : EnumSet.range(PROFILER_0, PROFILER_9))
      if (REGISTRY.get(mapping).matches(key, scancode))
        return mapping.getKeyCode();
    return GLFW_KEY_UNKNOWN;
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

  @WrapOperation(
      method = "handleDebugKeys",
      at = @At(
          value = "NEW",
          target = "(Ljava/lang/String;)Lnet/minecraft/network/chat/TranslatableComponent;"
      )
  )
  private TranslatableComponent handleDebugKeys_newTranslatableComponent(String key,
                                                                         Operation<TranslatableComponent> original)
  {
    return new RemappedTranslatableComponent(REGISTRY.getByDebugHelp(key), key);
  }

  @WrapOperation(
      method = "debugFeedbackTranslated",
      at = @At(
          value = "NEW",
          target = "(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/TranslatableComponent;"
      )
  )
  private TranslatableComponent debugFeedbackTranslated_newTranslatableComponent(
      String key, Object[] args, Operation<TranslatableComponent> original)
  {
    return "debug.crash.message".equals(key)
        ? new RemappedTranslatableComponent(REGISTRY.get(COPY_LOCATION), key)
        : original.call(key, args);
  }
}
