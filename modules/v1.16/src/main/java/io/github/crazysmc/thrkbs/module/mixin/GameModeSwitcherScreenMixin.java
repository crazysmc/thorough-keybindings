package io.github.crazysmc.thrkbs.module.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import io.github.crazysmc.thrkbs.core.MappingRegistry;
import io.github.crazysmc.thrkbs.core.ThoroughKeybindings;
import io.github.crazysmc.thrkbs.core.api.KeyDisplay;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.network.chat.TranslatableComponent;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin
{
  @Unique
  private static final MappingRegistry MAPPING_REGISTRY = ThoroughKeybindings.getMappingRegistry();
  @Unique
  private static final KeyDisplay KEY_DISPLAY = KeyDisplay.getProvider();

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
