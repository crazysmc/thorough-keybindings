package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.keyBinding;
import static io.github.crazysmc.thrkbs.HardcodedMapping.GAME_MODE;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(GameModeSwitcherScreen.class)
public abstract class GameModeSwitcherScreenMixinOld
{
  @Definition(
      id = "get",
      method = "Lnet/minecraft/client/resources/language/I18n;get(Ljava/lang/String;[Ljava/lang/Object;)" +
          "Ljava/lang/String;"
  )
  @Expression("get('debug.gamemodes.press_f4', ?)")
  @WrapOperation(method = "render", at = @At("MIXINEXTRAS:EXPRESSION"))
  private String render_pressF4(String key, Object[] args, Operation<String> original)
  {
    return keyBinding(REGISTRY.get(GAME_MODE).getTranslatedKeyText());
  }
}
