package io.github.crazysmc.thrkbs.module.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.network.chat.TranslatableComponent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.KEY_DISPLAY;
import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin
{
  @ModifyReceiver(method = "<clinit>",
                  at = @At(value = "INVOKE",
                           target = "Lnet/minecraft/network/chat/TranslatableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;"))
  private static TranslatableComponent remapF4Text(TranslatableComponent instance, ChatFormatting chatFormatting)
  {
    if (!"debug.gamemodes.press_f4".equals(instance.getKey()))
      return instance;
    String f4 = KEY_DISPLAY.getDisplayName(MAPPING_REGISTRY.getMapping(GLFW.GLFW_KEY_F4));
    return new TranslatableComponent("debug.gamemodes.press_key", f4);
  }

  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = GLFW.GLFW_KEY_F4))
  private int remapF4Key(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }
}
