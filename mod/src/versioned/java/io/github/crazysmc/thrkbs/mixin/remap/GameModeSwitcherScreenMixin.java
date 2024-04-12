//$if >=1.16
package io.github.crazysmc.thrkbs.mixin.remap;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import io.github.crazysmc.thrkbs.CustomKeyMapping;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin
{
  //$if >=1.16 <1.19
  @ModifyReceiver(method = "<clinit>",
                  at = @At(value = "INVOKE",
                           target = "Lnet/minecraft/network/chat/TranslatableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;"))
  private static net.minecraft.network.chat.TranslatableComponent remapF4Text(
      net.minecraft.network.chat.TranslatableComponent instance, ChatFormatting chatFormatting)
  {
    if (!"debug.gamemodes.press_f4".equals(instance.getKey()))
      return instance;
    String key = CustomKeyMapping.getDisplayNameByOriginal(GLFW.GLFW_KEY_F4);
    return new net.minecraft.network.chat.TranslatableComponent("debug.gamemodes.press_key", key);
  }

  //$if >=1.19
  @ModifyReceiver(method = "<clinit>",
                  at = @At(value = "INVOKE",
                           target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;"))
  private static MutableComponent remapF4Text(MutableComponent instance, ChatFormatting chatFormatting)
  {
    net.minecraft.network.chat.ComponentContents contents = instance.getContents();
    if (!(contents instanceof net.minecraft.network.chat.contents.TranslatableContents) ||
        !"debug.gamemodes.press_f4".equals(
            ((net.minecraft.network.chat.contents.TranslatableContents) contents).getKey()))
      return instance;
    String key = CustomKeyMapping.getDisplayNameByOriginal(GLFW.GLFW_KEY_F4);
    return Component.translatable("debug.gamemodes.press_key", key);
  }

  //$if >=1.16
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = GLFW.GLFW_KEY_F4))
  private int remapF4(int constant)
  {
    return CustomKeyMapping.getKeyCodeByOriginal(constant);
  }
}
