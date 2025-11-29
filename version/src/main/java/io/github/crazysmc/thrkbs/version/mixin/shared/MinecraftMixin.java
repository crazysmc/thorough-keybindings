package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.crazysmc.thrkbs.version.KeyRebinding;
import io.github.crazysmc.thrkbs.version.shared.RemappedTranslatableText;
import net.minecraft.client.Minecraft;
import net.minecraft.text.TranslatableText;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;
import static org.lwjgl.input.Keyboard.KEY_ESCAPE;
import static org.lwjgl.input.Keyboard.KEY_NONE;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @ModifyVariable(method = "handleKeyboardEvents", at = @At("STORE"))
  private int handleKeyboardEvents_setKeyCode(int keyCode)
  {
    KeyRebinding.setPressed(keyCode, Keyboard.getEventKeyState());
    return keyCode;
  }

  @WrapOperation(
      method = "handleKeyboardEvents",
      at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z")
  )
  private boolean handleKeyboardEvents_isKeyDown(int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isPressed();
  }

  @Definition(id = "key", local = @Local(type = int.class))
  @Expression("key == @(?)")
  @ModifyExpressionValue(method = "handleKeyboardEvents", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int handleKeyboardEvents_keyEqConst(int constant)
  {
    KeyRebinding rebinding = REGISTRY.getByDefault(constant);
    return constant == KEY_ESCAPE && rebinding.isUnbound() ? constant : rebinding.getKeyCode();
  }

  @Definition(id = "key", local = @Local(type = int.class))
  @Expression("key == @(48)")
  @ModifyExpressionValue(method = "handleGuiKeyBindings", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int handleGuiKeyBindings_keyEqB(int constant)
  {
    return REGISTRY.getByDefault(constant).getKeyCode();
  }

  @WrapOperation(
      method = "handleKeyboardEvents",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;handleDebugKey(I)Z")
  )
  private boolean handleKeyboardEvents_handleDebugKey(Minecraft instance, int keyCode, Operation<Boolean> original)
  {
    for (KeyRebinding binding : REGISTRY.getDebugKeys())
      if (binding.getKeyCode() == keyCode)
        return original.call(instance, binding.getDefaultKeyCode());
    return original.call(instance, KEY_NONE);
  }

  @WrapOperation(
      method = "handleDebugKey",
      at = @At(
          value = "NEW",
          target = "(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/text/TranslatableText;"
      )
  )
  private TranslatableText handleDebugKey_newTranslatableText(String key, Object[] args,
                                                              Operation<TranslatableText> original)
  {
    return new RemappedTranslatableText(REGISTRY.getByDebugHelp(key), key);
  }
}
