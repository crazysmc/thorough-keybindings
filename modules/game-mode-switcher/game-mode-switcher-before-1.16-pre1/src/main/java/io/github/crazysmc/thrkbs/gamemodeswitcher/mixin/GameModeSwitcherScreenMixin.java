package io.github.crazysmc.thrkbs.gamemodeswitcher.mixin;

import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.KEY_DISPLAY;
import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin
{
  @ModifyConstant(method = "keyPressed", constant = @Constant(intValue = GLFW.GLFW_KEY_F4))
  private int remapF4KeyConstant(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }

  @ModifyVariable(method = "drawKeyOption", at = @At("LOAD"), ordinal = 0, argsOnly = true)
  private String replaceF4KeyText(String string)
  {
    return "F4".equals(string) ? KEY_DISPLAY.getDisplayName(MAPPING_REGISTRY.getMapping(GLFW.GLFW_KEY_F4)) : string;
  }
}