package io.github.crazysmc.thrkbs.gamemodeswitcher.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.network.chat.Component;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F4;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = GLFW_KEY_F4))
  private int remapF4KeyConstant(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }

  @Redirect(method = "render",
            at = @At(value = "FIELD",
                     target = "Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen;SELECT_KEY:Lnet/minecraft/network/chat/Component;",
                     opcode = Opcodes.GETSTATIC))
  private Component replaceF4KeyText()
  {
    String f4 = KEY_DISPLAY.getDisplayName(MAPPING_REGISTRY.getMapping(GLFW_KEY_F4));
    Component styled = CHAT_COMPONENTS.translatableWithStyle(ChatFormatting.AQUA, "debug.gamemodes.press_key", f4);
    return CHAT_COMPONENTS.translatable("debug.gamemodes.select_next", styled);
  }
}
