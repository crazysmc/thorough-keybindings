package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.keyBinding;
import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MODE;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.objectweb.asm.Opcodes.GETSTATIC;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin
{
  @WrapOperation(
      method = "checkToClose",
      at = @At(
          value = "INVOKE",
          target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(Lcom/mojang/blaze3d/platform/Window;I)Z"
      )
  )
  private boolean checkToClose_isKeyDown(Window window, int constant, Operation<Boolean> original)
  {
    return REGISTRY.getByDefault(constant).isDown();
  }

  @Expression("? == 293")
  @ModifyExpressionValue(method = "keyPressed", at = @At("MIXINEXTRAS:EXPRESSION"))
  private boolean keyPressed_intEqConst(boolean original, KeyEvent keyEvent)
  {
    return REGISTRY.get(GAME_MODE).matches(keyEvent);
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
