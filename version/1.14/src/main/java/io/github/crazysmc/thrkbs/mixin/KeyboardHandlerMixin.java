package io.github.crazysmc.thrkbs.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.crazysmc.thrkbs.HardcodedMapping;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.debugHelpMessage;
import static io.github.crazysmc.thrkbs.HardcodedMapping.DEBUG_INFO;
import static io.github.crazysmc.thrkbs.InitVersion.*;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @Unique
  private static final Logger LOGGER = LogManager.getLogger();

  @Unique
  private static Component replaceDebugCombo(HardcodedMapping mapping, String text)
  {
    KeyMapping f3 = (KeyMapping) DEBUG_INFO.object;
    KeyMapping key = (KeyMapping) mapping.object;
    return new TextComponent(debugHelpMessage(text, f3.getTranslatedKeyMessage(), key.getTranslatedKeyMessage()));
  }

  @ModifyVariable(method = "keyPress", at = @At("HEAD"), argsOnly = true, ordinal = 0)
  private int keyPress(int key, long _window, int _key, int scancode)
  {
    for (HardcodedMapping mapping : KEYBOARD_HANDLER_MAPPINGS)
      if (((KeyMapping) mapping.object).matches(key, scancode))
        return mapping.getKeyCode();
    return key;
  }

  @WrapOperation(
      method = "keyPress",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyboardHandler;handleDebugKeys(I)Z")
  )
  private boolean keyPress_handleDebugKeys(KeyboardHandler instance, int key, Operation<Boolean> original,
                                           long _window, int _key, int scancode)
  {
    for (HardcodedMapping mapping : DEBUG_KEYS_MAPPINGS)
      if (((KeyMapping) mapping.object).matches(key, scancode))
        return original.call(instance, mapping.getKeyCode());
    return original.call(instance, key);
  }

  @WrapOperation(
      method = "handleDebugKeys",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage" +
              "(Lnet/minecraft/network/chat/Component;)V"
      )
  )
  private void handleDebugKeys_addMessage(ChatComponent instance, Component component, Operation<Void> original)
  {
    if (component instanceof TranslatableComponent)
    {
      TranslatableComponent translatable = (TranslatableComponent) component;
      HardcodedMapping mapping = DEBUG_KEYS_HELP.get(translatable.getKey());
      if (mapping != null)
        component = replaceDebugCombo(mapping, translatable.getContents());
      else
        LOGGER.warn("expected one of {}", DEBUG_KEYS_HELP::keySet);
    }
    else
      LOGGER.warn("expected debug help message with translation key");
    original.call(instance, component);
  }
}
