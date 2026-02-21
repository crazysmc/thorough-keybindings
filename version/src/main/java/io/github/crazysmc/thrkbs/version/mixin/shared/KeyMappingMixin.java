package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

import static io.github.crazysmc.thrkbs.ThoroughKeybindings.MULTIMAP;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin
{
  /**
   * Replacing this map with a multimap (map to list of values) to support duplicate bindings.
   */
  @Shadow
  @Final
  private static Map<InputConstants.Key, KeyMapping> MAP;

  @Shadow
  private int clickCount;

  @Shadow
  private boolean isDown;

  @Inject(method = "click", at = @At("HEAD"), cancellable = true)
  private static void click(InputConstants.Key key, CallbackInfo ci)
  {
    List<KeyMapping> list = MULTIMAP.get(key);
    if (list != null)
      for (KeyMapping mapping : list)
        ((KeyMappingMixin) (Object) mapping).clickCount++;
    ci.cancel();
  }

  @Inject(method = "set", at = @At("HEAD"), cancellable = true)
  private static void set(InputConstants.Key key, boolean isDown, CallbackInfo ci)
  {
    List<KeyMapping> list = MULTIMAP.get(key);
    if (list != null)
      for (KeyMapping mapping : list)
        ((KeyMappingMixin) (Object) mapping).method_23481(isDown);
    ci.cancel();
  }

  @Inject(method = "resetMapping", at = @At(value = "HEAD"))
  private static void resetMapping(CallbackInfo ci)
  {
    MULTIMAP.clear();
  }

  @WrapOperation(
      method = {
          "resetMapping",
          "<init>(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V"
      },
      at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;")
  )
  private static Object resetMapping_put(Map<InputConstants.Key, KeyMapping> map, Object key, Object value,
                                         Operation<Object> original)
  {
    if (map != MAP)
      return original.call(map, key, value);
    MULTIMAP.computeIfAbsent(((InputConstants.Key) key), k -> new ArrayList<>()).add((KeyMapping) value);
    return null;
  }

  @Unique(silent = true) /* setDown available since 19w41a */
  public void method_23481(boolean isDown)
  {
    this.isDown = isDown;
  }

  @Inject(method = "same", at = @At("HEAD"), cancellable = true)
  private void same(KeyMapping keyMapping, CallbackInfoReturnable<Boolean> cir)
  {
    if ((Object) this instanceof KeyRemapping != keyMapping instanceof KeyRemapping)
      cir.setReturnValue(false);
  }
}
