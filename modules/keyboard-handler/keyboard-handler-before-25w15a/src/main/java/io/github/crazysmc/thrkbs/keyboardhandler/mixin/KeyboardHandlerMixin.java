package io.github.crazysmc.thrkbs.keyboardhandler.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import static io.github.crazysmc.thrkbs.core.HardcodedMapping.DEBUG_KEYS;
import static io.github.crazysmc.thrkbs.core.MappingRegistry.MAPPING_REGISTRY;
import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.CHAT_COMPONENTS;
import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.DYNAMIC_TEXT_REPLACER;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @ModifyVariable(method = "handleDebugKeys", at = @At("LOAD"), argsOnly = true)
  private int remapDebugKeySwitch(int keyCode)
  {
    for (int debugKey : DEBUG_KEYS)
      if (keyCode == MAPPING_REGISTRY.remapKeyCode(debugKey))
        return debugKey;
    return -1;
  }

  @WrapOperation(
      method = "handleDebugKeys",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/gui/components/ChatComponent;" +
              "addMessage(Lnet/minecraft/network/chat/Component;)V"
      )
  )
  private void replaceDebugHelpListText(ChatComponent instance, Component component, Operation<Void> original)
  {
    original.call(instance, CHAT_COMPONENTS.literal(DYNAMIC_TEXT_REPLACER.debugHelpList(component.getString())));
  }

  @ModifyIntIfEqual(method = "keyPress", constant = @Constant)
  private int remapKeyConstants(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }
}
