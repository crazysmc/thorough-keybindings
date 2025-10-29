package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin
{
  /**
   * changing the map into a multimap (map to list of values) to support duplicate bindings
   */
  @Shadow
  @Final
  private static Map<InputConstants.Key, List<KeyMapping>> MAP;

  @Shadow
  private int clickCount;

  @Shadow
  private boolean isDown;

  @Inject(method = "click", at = @At("HEAD"), cancellable = true)
  private static void click(InputConstants.Key key, CallbackInfo ci)
  {
    List<KeyMapping> list = MAP.get(key);
    if (list != null)
      for (KeyMapping keyMapping : list)
        ((KeyMappingMixin) (Object) keyMapping).clickCount++;
    ci.cancel();
  }

  @Inject(method = "set", at = @At("HEAD"), cancellable = true)
  private static void set(InputConstants.Key key, boolean isDown, CallbackInfo ci)
  {
    List<KeyMapping> list = MAP.get(key);
    if (list != null)
      for (KeyMapping keyMapping : list)
        ((KeyMappingMixin) (Object) keyMapping).isDown = isDown;
    ci.cancel();
  }

  @WrapOperation(
      method = {
          "resetMapping",
          "<init>(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V"
      },
      at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")
  )
  private static Object resetMapping_put(Map<InputConstants.Key, List<KeyMapping>> map, Object key, Object value,
                                         Operation<Object> original)
  {
    if (map != MAP)
      return original.call(map, key, value);
    MAP.computeIfAbsent(((InputConstants.Key) key), k -> new ArrayList<>()).add((KeyMapping) value);
    return null;
  }

//  @Shadow
//  @Final
//  private String category;
//
//  @Inject(method = "same", at = @At("HEAD"), cancellable = true)
//  private void separateDebugCombos(KeyMapping keyMapping, CallbackInfoReturnable<Boolean> cir)
//  {
//    if (DEBUG.equals(category) != DEBUG.equals(keyMapping.getCategory()) ||
//        MODIFIER.equals(category) != MODIFIER.equals(keyMapping.getCategory()))
//      cir.setReturnValue(false);
//  }
}
