package io.github.crazysmc.thrkbs.module.mixin;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.DYNAMIC_TEXT_REPLACER;
import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @Redirect(method = "method_1454",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/components/events/ContainerEventHandler;keyPressed(III)Z"))
  private boolean remapEscape(ContainerEventHandler instance, int key, int scancode, int action)
  {
    boolean keepKey = key != MAPPING_REGISTRY.remapKeyCode(GLFW.GLFW_KEY_ESCAPE) || instance instanceof ControlsScreen;
    return instance.keyPressed(keepKey ? key : GLFW.GLFW_KEY_ESCAPE, scancode, action);
  }

  @Redirect(method = "handleDebugKeys",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;)V"))
  private void debugHelpText(ChatComponent instance, Component component)
  {
    instance.addMessage(new TextComponent(DYNAMIC_TEXT_REPLACER.replaceF3Combos(component.getString())));
  }
}
