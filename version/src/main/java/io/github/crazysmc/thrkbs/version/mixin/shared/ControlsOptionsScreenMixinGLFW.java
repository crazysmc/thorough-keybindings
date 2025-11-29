package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.crazysmc.thrkbs.version.KeyRebinding;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MENU;
import static io.github.crazysmc.thrkbs.version.KeyRebinding.REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

@Mixin(ControlsOptionsScreen.class)
public abstract class ControlsOptionsScreenMixinGLFW
{
  @Definition(id = "key", local = @Local(type = int.class, ordinal = 0, argsOnly = true))
  @Expression("key == 256")
  @ModifyExpressionValue(method = { "keyPressed", "m_6815938" }, at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPressed_keyEqEscape(boolean original, int key, int scancode)
  {
    KeyRebinding rebinding = REGISTRY.get(GAME_MENU);
    return !rebinding.isUnbound() ? rebinding.matches(key, scancode) : original;
  }

  @Definition(id = "key", local = @Local(type = int.class, ordinal = 0, argsOnly = true))
  @Expression("super.?(@(key), ?, ?)")
  @ModifyExpressionValue(method = { "keyPressed", "m_6815938" }, at = @At("MIXINEXTRAS:EXPRESSION"))
  private int keyPressed_super(int key, int scancode, int action)
  {
    return REGISTRY.get(GAME_MENU).matches(key, scancode) ? GLFW_KEY_ESCAPE : key;
  }
}
