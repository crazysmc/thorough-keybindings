package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MENU;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixinControls
{
  @Shadow
  @Final
  private Minecraft minecraft;

  @Definition(id = "key", local = @Local(type = int.class, ordinal = 0, argsOnly = true))
  @Definition(id = "keyHandled", local = @Local(type = boolean[].class))
  @Definition(id = "boolean", type = boolean.class)
  @Expression(id = "load", value = "key")
  @Expression(id = "from", value = "keyHandled = @(new boolean[] { false })")
  @Expression(id = "to", value = "keyHandled[0]")
  @ModifyExpressionValue(
      method = "keyPress",
      at = @At(id = "load", value = "MIXINEXTRAS:EXPRESSION"),
      slice = @Slice(
          from = @At(id = "from", value = "MIXINEXTRAS:EXPRESSION:ONE"),
          to = @At(id = "to", value = "MIXINEXTRAS:EXPRESSION:ONE")
      ),
      allow = 1
  )
  private int keyPress_withBooleanArray_key(int key, long window, int _key, int scancode)
  {
    return !(minecraft.screen instanceof ControlsScreen) && REGISTRY.get(GAME_MENU).matches(key, scancode)
        ? GLFW_KEY_ESCAPE
        : key;
  }
}
