package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.crazysmc.thrkbs.version.shared.RemappedTranslatableComponent;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.COPY_LOCATION;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixinConstructor
{
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
