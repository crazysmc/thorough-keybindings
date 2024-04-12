//$if >=1.16
package io.github.crazysmc.thrkbs.mixin.remap;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import io.github.crazysmc.thrkbs.CustomKeyMapping;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.network.chat.TranslatableComponent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin
{
  @ModifyReceiver(method = "<clinit>",
                  at = @At(value = "INVOKE",
                           target = "Lnet/minecraft/network/chat/TranslatableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;"))
  private static TranslatableComponent remapF4Text(TranslatableComponent instance, ChatFormatting chatFormatting)
  {
    if ("debug.gamemodes.press_f4".equals(instance.getKey()))
      return new TranslatableComponent("debug.gamemodes.press_key",
                                       CustomKeyMapping.getDisplayNameByOriginal(GLFW.GLFW_KEY_F4));
    return instance;
  }

  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = GLFW.GLFW_KEY_F4))
  private int remapF4(int constant)
  {
    return CustomKeyMapping.getKeyCodeByOriginal(constant);
  }
}
