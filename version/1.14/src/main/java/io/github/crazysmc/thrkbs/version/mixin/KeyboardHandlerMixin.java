package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.version.KeyRemapping;
import io.github.crazysmc.thrkbs.version.RemappedTranslatableComponent;
import net.minecraft.client.*;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.mojang.blaze3d.platform.InputConstants.getKey;
import static org.lwjgl.glfw.GLFW.*;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @Shadow
  @Final
  private Minecraft minecraft;

  @Unique
  private InputConstants.Key addedKey;

  @Inject(method = "keyPress", at = @At("HEAD"))
  private void keyPress(long window, int key, int scancode, int action, int mods, CallbackInfo ci)
  {
    if (window != minecraft.window.getWindow())
      return;
    addedKey = getKey(key, scancode);
    KeyMapping.set(addedKey, action != GLFW_RELEASE);
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
    return KeyRemapping.getByDefault(constant).isDown();
  }

  @Definition(id = "key", local = @Local(type = int.class, argsOnly = true, ordinal = 0))
  @Expression("key == @(?)")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int keyPress_intEqConst(int constant, long window, int key, int scancode)
  {
    return KeyRemapping.getByDefault(constant).matches(key, scancode) ? key : GLFW_KEY_UNKNOWN;
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
    return KeyRemapping.getByDefault(GLFW_KEY_ESCAPE).matches(key, scancode) ? GLFW_KEY_ESCAPE : key;
  }

  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyboardHandler;handleDebugKeys(I)Z")
  )
  private boolean keyPress_handleDebugKeys(KeyboardHandler instance, int key, Operation<Boolean> original,
                                           long window, int _key, int scancode)
  {
    for (KeyRemapping mapping : KeyRemapping.getDebugKeys())
      if (mapping.matches(key, scancode))
        return original.call(instance, mapping.getDefaultKey().getValue());
    return original.call(instance, -1);
  }

  @WrapOperation(
      method = "handleDebugKeys",
      at = @At(
          value = "NEW",
          target = "(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/TranslatableComponent;"
      )
  )
  private TranslatableComponent handleDebugKeys_newTranslatableComponent(String key, Object[] args,
                                                                         Operation<TranslatableComponent> original)
  {
    return new RemappedTranslatableComponent(KeyRemapping.getByDebugHelp(key), key, args);
  }
}
