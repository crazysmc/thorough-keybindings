package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.keyBinding;
import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MODE;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;
import static org.objectweb.asm.Opcodes.GETSTATIC;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin
{
  @WrapOperation(
      method = "checkToClose",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z")
  )
  private boolean checkToClose_isKeyDown(long window, int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isDown();
  }

  @Definition(id = "key", local = @Local(type = int.class, argsOnly = true, ordinal = 0))
  @Expression("key == @(293)")
  @ModifyExpressionValue(method = "keyPressed", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int keyPressed_intEqConst(int constant, int key, int scancode)
  {
    return REGISTRY.get(GAME_MODE).matches(key, scancode) ? key : GLFW_KEY_UNKNOWN;
  }

  @WrapOperation(
      method = "render",
      at = @At(
          value = "FIELD",
          target = "Lnet/minecraft/client/gui/screens/debug/GameModeSwitcherScreen;" +
              "SELECT_KEY:Lnet/minecraft/network/chat/Component;",
          opcode = GETSTATIC
      )
  )
  private Component render_selectKey(Operation<Component> original)
  {
    String f4 = REGISTRY.get(GAME_MODE).getTranslatedKeyText();
    MutableComponent component = Component.translatable("debug.gamemodes.press_key", keyBinding(f4));
    return Component.translatable("debug.gamemodes.select_next", component.withStyle(ChatFormatting.AQUA));
  }
}
