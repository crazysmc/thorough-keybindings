package io.github.crazysmc.thrkbs.remapescape.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.gui.GuiEventListener;
import net.minecraft.client.gui.chat.ChatGui;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin
{
  @ModifyVariable(method = "handleDebugKeys", at = @At("LOAD"), argsOnly = true)
  private int remapDebugKeySwitch(int keyCode)
  {
    for (int debugKey : KEY_CODES.getDebugKeys())
      if (keyCode == MAPPING_REGISTRY.remapKeyCode(debugKey))
        return debugKey;
    return -1;
  }

  @Redirect(method = "handleDebugKeys",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/chat/ChatGui;addMessage(Lnet/minecraft/text/Text;)V"))
  private void replaceDebugHelpListText(ChatGui instance, Text component)
  {
    instance.addMessage(CHAT_COMPONENTS.literal(DYNAMIC_TEXT_REPLACER.debugHelpList(component.getString())));
  }

  @ModifyIntIfEqual(method = "keyPress", constant = @Constant)
  private int remapKeyConstants(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }

  @ModifyArg(method = "m_8874045",
             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiEventListener;keyPressed(III)Z"),
             index = 0)
  private int remapEscapeKey(int keyCode, @Local(argsOnly = true) GuiEventListener instance)
  {
    return keyCode != MAPPING_REGISTRY.remapKeyCode(GLFW_KEY_ESCAPE) ||
        instance instanceof ControlsOptionsScreen ? keyCode : GLFW_KEY_ESCAPE;
  }
}
