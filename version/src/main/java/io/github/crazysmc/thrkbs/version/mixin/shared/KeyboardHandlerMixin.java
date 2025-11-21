package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.version.KeyRebinding;
import io.github.crazysmc.thrkbs.version.shared.RemappedTranslatableText;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import static io.github.crazysmc.thrkbs.HardcodedMapping.COPY_LOCATION;
import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MENU;
import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;
import static org.lwjgl.glfw.GLFW.*;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @Shadow
  @Final
  private Minecraft minecraft;

  @Definition(id = "window", local = @Local(type = long.class, argsOnly = true))
  @Expression("window == ?")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPress_windowEq(boolean original, long window, int key, int scancode, int action)
  {
    if (original)
      KeyRebinding.setPressed(InputConstants.getKey(key, scancode), action != GLFW_RELEASE);
    return original;
  }

  @Group(name = "getKey")
  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;getKey(JI)Z")
  )
  private boolean keyPress_getKey(long window, int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isPressed();
  }

  @Group(name = "getKey")
  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;getKey(I)Z")
  )
  private boolean keyPress_getKey(int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isPressed();
  }

  @Definition(id = "key", local = @Local(type = int.class, argsOnly = true, ordinal = 0))
  @Expression("key == @(?)")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int keyPress_keyEqConst(int constant, long window, int key, int scancode)
  {
    if (constant == GLFW_KEY_TAB || constant >= GLFW_KEY_RIGHT && constant <= GLFW_KEY_UP)
      return constant;
    KeyRebinding rebinding = REGISTRY.getByDefault(constant);
    if (constant == GLFW_KEY_ESCAPE && rebinding.isUnbound())
      return constant;
    return rebinding.matches(key, scancode) ? key : key + 1;
  }

  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyboardHandler;handleDebugKeys(I)Z")
  )
  private boolean keyPress_handleDebugKeys(KeyboardHandler instance, int key, Operation<Boolean> original,
                                           long window, int _key, int scancode)
  {
    for (KeyRebinding binding : REGISTRY.getDebugKeys())
      if (binding.matches(key, scancode))
        return original.call(instance, binding.getDefaultKey().getValue());
    return original.call(instance, GLFW_KEY_UNKNOWN);
  }

  @Definition(id = "key", local = @Local(type = int.class, ordinal = 0, argsOnly = true))
  @Definition(id = "keyHandled", local = @Local(type = boolean[].class))
  @Definition(id = "boolean", type = boolean.class)
  @Expression(id = "load", value = "key")
  @Expression(id = "from", value = "keyHandled = @(new boolean[] { false })")
  @Expression(id = "to", value = "keyHandled[0]")
  @ModifyExpressionValue(
      method = "keyPress",
      at = @At(id = "load", value = "MIXINEXTRAS:EXPRESSION"),
      slice = @Slice(
          from = @At(id = "from", value = "MIXINEXTRAS:EXPRESSION:ONE"),
          to = @At(id = "to", value = "MIXINEXTRAS:EXPRESSION:ONE")
      ),
      allow = 1
  )
  private int keyPress_withBooleanArray_key(int key, long window, int _key, int scancode)
  {
    return !(minecraft.screen instanceof ControlsOptionsScreen) && REGISTRY.get(GAME_MENU).matches(key, scancode)
        ? GLFW_KEY_ESCAPE
        : key;
  }

  @WrapOperation(
      method = "handleDebugKeys",
      at = @At(
          value = "NEW",
          target = "(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/text/TranslatableText;"
      )
  )
  private TranslatableText handleDebugKeys_newTranslatableText(String key, Object[] args,
                                                               Operation<TranslatableText> original)
  {
    return new RemappedTranslatableText(REGISTRY.getByDebugHelp(key), key);
  }

  @WrapOperation(
      method = "sendDebugInfo",
      at = @At(
          value = "NEW",
          target = "(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/text/TranslatableText;"
      )
  )
  private TranslatableText sendDebugInfo_newTranslatableText(String key, Object[] args,
                                                             Operation<TranslatableText> original)
  {
    return "debug.crash.message".equals(key)
        ? new RemappedTranslatableText(REGISTRY.get(COPY_LOCATION), key)
        : original.call(key, args);
  }
}
