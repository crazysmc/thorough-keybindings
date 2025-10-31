package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.crazysmc.thrkbs.version.KeyRemapping;
import io.github.crazysmc.thrkbs.version.RemappedTranslatableComponent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MODE;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixin_20w20a
{
  @Definition(id = "TranslatableComponent", type = TranslatableComponent.class)
  @Expression("new TranslatableComponent('debug.gamemodes.press_f4')")
  @WrapOperation(method = "<clinit>", at = @At("MIXINEXTRAS:EXPRESSION"))
  private static TranslatableComponent classInit_pressF4(String key, Operation<TranslatableComponent> original)
  {
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    String f4 = REGISTRY.get(GAME_MODE).getTranslatedKeyMessage().visitSelf(Optional::of).get();
    return new TranslatableComponent("chat.square_brackets", String.format(" %s ", f4));
  }

  @WrapOperation(
      method = "checkToClose",
      at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;isKeyDown(JI)Z")
  )
  private boolean checkToClose_isKeyDown(long window, int constant, Operation<Boolean> original)
  {
    System.out.println("isDown = " + REGISTRY.getByDefault(constant).isDown());
    return REGISTRY.getByDefault(constant).isDown();//FIXME
  }

  @Definition(id = "key", local = @Local(type = int.class, argsOnly = true, ordinal = 0))
  @Expression("key == @(293)")
  @ModifyExpressionValue(method = "keyPressed", at = @At("MIXINEXTRAS:EXPRESSION"))
  private int keyPressed_intEqConst(int constant, int key, int scancode)
  {
    return REGISTRY.get(GAME_MODE).matches(key, scancode) ? key : GLFW_KEY_UNKNOWN;
  }
}
