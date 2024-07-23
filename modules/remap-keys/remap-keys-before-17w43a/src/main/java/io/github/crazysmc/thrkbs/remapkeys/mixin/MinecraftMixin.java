package io.github.crazysmc.thrkbs.remapkeys.mixin;

import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.ChatGui;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Redirect;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.*;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @ModifyIntIfEqual(method = { "handleKeyboardEvents", "handleDebugKey" }, constant = @Constant)
  private int remapKeyConstants(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }

  @Redirect(method = "handleDebugKey",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/gui/chat/ChatGui;addMessage(Lnet/minecraft/text/Text;)V"))
  private void replaceDebugHelpListText(ChatGui instance, Text component)
  {
    instance.addMessage(CHAT_COMPONENTS.literal(DYNAMIC_TEXT_REPLACER.debugHelpList(component.getString())));
  }
}
