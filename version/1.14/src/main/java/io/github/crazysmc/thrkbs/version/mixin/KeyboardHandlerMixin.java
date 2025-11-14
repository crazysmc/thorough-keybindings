package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.crazysmc.thrkbs.version.RemappedTranslatableText;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.COPY_LOCATION;
import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
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
