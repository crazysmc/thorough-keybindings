package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MENU;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixinKeyBindsOpt
{
  @Shadow
  @Final
  private Minecraft minecraft;

  @Definition(id = "screen", local = @Local(type = Screen.class))
  @Definition(id = "key", local = @Local(type = int.class, ordinal = 0, argsOnly = true))
  @Expression("screen.?(@(key), ?, ?)")
  @ModifyExpressionValue(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int keyPress_screenMethod_key(int key, long window, int _key, int scancode)
  {
    return !(minecraft.screen instanceof KeyBindsScreen) && REGISTRY.get(GAME_MENU).matches(key, scancode)
        ? GLFW_KEY_ESCAPE
        : key;
  }
}
