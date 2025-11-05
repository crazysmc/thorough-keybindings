package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.crazysmc.thrkbs.version.shared.RemappedTranslatableContents;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static io.github.crazysmc.thrkbs.HardcodedMapping.COPY_LOCATION;
import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MENU;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @ModifyArg(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;keyPressed(III)Z"),
      index = 0
  )
  private static int keyPress_keyPressed(int key, int scancode, int mods)
  {
    return REGISTRY.get(GAME_MENU).matches(key, scancode) ? GLFW_KEY_ESCAPE : key;
  }

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
          target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)" +
              "Lnet/minecraft/network/chat/MutableComponent;"
      )
  )
  private MutableComponent debugFeedbackTranslated_translatable(
      String key, Operation<MutableComponent> original)
  {
    return "debug.crash.message".equals(key)
        ? MutableComponent.create(new RemappedTranslatableContents(REGISTRY.get(COPY_LOCATION), key))
        : original.call(key);
  }
}
