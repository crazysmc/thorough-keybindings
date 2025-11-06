package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.crazysmc.thrkbs.version.KeyRemapping;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MENU;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

@Mixin(ControlsScreen.class)
public abstract class ControlsScreenMixin
{
  @Definition(id = "key", local = @Local(type = int.class, ordinal = 0, argsOnly = true))
  @Expression("key == 256")
  @ModifyExpressionValue(method = "keyPressed", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPressed_keyEqEscape(boolean original, int key, int scancode)
  {
    KeyRemapping remapping = REGISTRY.get(GAME_MENU);
    return !remapping.isUnbound() ? remapping.matches(key, scancode) : original;
  }

  @Definition(id = "key", local = @Local(type = int.class, ordinal = 0, argsOnly = true))
  @Expression("super.?(@(key), ?, ?)")
  @ModifyExpressionValue(method = "keyPressed", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int keyPressed_super(int key, int scancode, int action)
  {
    return REGISTRY.get(GAME_MENU).matches(key, scancode) ? GLFW_KEY_ESCAPE : key;
  }
}
