package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.crazysmc.thrkbs.version.RemappedTranslatableContents;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.COPY_LOCATION;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixinComponent
{
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
          target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)" +
              "Lnet/minecraft/network/chat/MutableComponent;"
      )
  )
  private MutableComponent debugFeedbackTranslated_translatable(
      String key, Object[] args, Operation<MutableComponent> original)
  {
    return "debug.crash.message".equals(key)
        ? MutableComponent.create(new RemappedTranslatableContents(REGISTRY.get(COPY_LOCATION), key))
        : original.call(key, args);
  }
}
