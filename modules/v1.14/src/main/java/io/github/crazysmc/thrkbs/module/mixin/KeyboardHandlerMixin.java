package io.github.crazysmc.thrkbs.module.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.DYNAMIC_TEXT_REPLACER;
import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @ModifyArg(method = "method_1454",
             at = @At(value = "INVOKE",
                      target = "Lnet/minecraft/client/gui/components/events/ContainerEventHandler;keyPressed(III)Z"),
             index = 0)
  private int remapEscape(int keyCode, @Local(argsOnly = true) ContainerEventHandler instance)
  {
    return keyCode != MAPPING_REGISTRY.remapKeyCode(GLFW.GLFW_KEY_ESCAPE) ||
        instance instanceof ControlsScreen ? keyCode : GLFW.GLFW_KEY_ESCAPE;
  }

  @Redirect(method = "handleDebugKeys",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;)V"))
  private void debugHelpText(ChatComponent instance, Component component)
  {
    instance.addMessage(new TextComponent(DYNAMIC_TEXT_REPLACER.replaceF3Combos(component.getString())));
  }
}
