package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.version.KeyRebinding;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixinGLFW
{
  /**
   * changing the map into a multimap (map to list of values) to support duplicate bindings
   */
  @Shadow
  @Final
  private static Map<InputConstants.Key, List<KeyBinding>> BY_KEY;

  @Shadow
  private int clickCount;

  @Shadow
  private boolean pressed;

  @Inject(method = "click", at = @At("HEAD"), cancellable = true)
  private static void click(InputConstants.Key key, CallbackInfo ci)
  {
    List<KeyBinding> list = BY_KEY.get(key);
    if (list != null)
      for (KeyBinding binding : list)
        ((KeyBindingMixinGLFW) (Object) binding).clickCount++;
    ci.cancel();
  }

  @Inject(method = "set", at = @At("HEAD"), cancellable = true)
  private static void set(InputConstants.Key key, boolean pressed, CallbackInfo ci)
  {
    List<KeyBinding> list = BY_KEY.get(key);
    if (list != null)
      for (KeyBinding binding : list)
        ((KeyBindingMixinGLFW) (Object) binding).pressed = pressed;
    ci.cancel();
  }

  @WrapOperation(
      method = {
          "resetMapping",
          "<init>(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V"
      },
      at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")
  )
  private static Object resetMapping_put(Map<InputConstants.Key, List<KeyBinding>> map, Object key, Object value,
                                         Operation<Object> original)
  {
    if (map != BY_KEY)
      return original.call(map, key, value);
    BY_KEY.computeIfAbsent(((InputConstants.Key) key), k -> new ArrayList<>()).add((KeyBinding) value);
    return null;
  }

  @Inject(method = "same", at = @At("HEAD"), cancellable = true)
  private void same(KeyBinding keyBinding, CallbackInfoReturnable<Boolean> cir)
  {
    if ((Object) this instanceof KeyRebinding != keyBinding instanceof KeyRebinding)
      cir.setReturnValue(false);
  }
}
