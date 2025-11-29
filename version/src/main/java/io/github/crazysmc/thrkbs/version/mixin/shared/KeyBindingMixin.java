package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.util.Int2ObjectHashMap;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin
{
  /**
   * changing the map into a multimap (map to list of values) to support duplicate bindings
   */
  @Shadow
  @Final
  private static Int2ObjectHashMap<List<KeyBinding>> BY_KEY_CODE;

  @Shadow
  private int clickCount;

  @Shadow
  private boolean pressed;

  @Inject(method = "click", at = @At("HEAD"), cancellable = true)
  private static void click(int keyCode, CallbackInfo ci)
  {
    List<KeyBinding> list = BY_KEY_CODE.get(keyCode);
    if (list != null)
      for (KeyBinding binding : list)
        ((KeyBindingMixin) (Object) binding).clickCount++;
    ci.cancel();
  }

  @Inject(method = "set", at = @At("HEAD"), cancellable = true)
  private static void set(int keyCode, boolean pressed, CallbackInfo ci)
  {
    List<KeyBinding> list = BY_KEY_CODE.get(keyCode);
    if (list != null)
      for (KeyBinding binding : list)
        ((KeyBindingMixin) (Object) binding).pressed = pressed;
    ci.cancel();
  }

  @WrapOperation(
      method = { "resetMapping", "<init>" },
      at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Int2ObjectHashMap;put(ILjava/lang/Object;)V")
  )
  private static void resetMapping_put(Int2ObjectHashMap<List<KeyBinding>> map, int key, Object value,
                                       Operation<Object> original)
  {
    if (map != BY_KEY_CODE)
    {
      original.call(map, key, value);
      return;
    }
    List<KeyBinding> list = BY_KEY_CODE.get(key);
    if (list == null)
      BY_KEY_CODE.put(key, list = new ArrayList<>());
    list.add((KeyBinding) value);
  }
}
