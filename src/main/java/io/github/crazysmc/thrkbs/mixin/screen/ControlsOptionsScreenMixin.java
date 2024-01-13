package io.github.crazysmc.thrkbs.mixin.screen;

import io.github.crazysmc.thrkbs.ControlsOptionsSubScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.locale.LanguageManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ControlsOptionsScreen.class)
public abstract class ControlsOptionsScreenMixin extends Screen
{
  @Redirect(method = "init",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/locale/LanguageManager;translate(Ljava/lang/String;)Ljava/lang/String;",
                     ordinal = 1))
  private String redirectTitle(LanguageManager languageManager, String key)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
      return languageManager.translate(((ControlsOptionsSubScreen) instance).getTitleKey());
    return languageManager.translate(key);
  }

  @Redirect(method = "*",
            at = @At(value = "FIELD",
                     target = "Lnet/minecraft/client/options/GameOptions;keyBindings:[Lnet/minecraft/client/options/KeyBinding;",
                     args = "array=length"))
  private int getKeyBindingsLength(KeyBinding[] keyBindings)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
      return ((ControlsOptionsSubScreen) instance).getKeyBindings().size();
    return keyBindings.length;
  }

  @Redirect(method = "render",
            at = @At(value = "FIELD",
                     target = "Lnet/minecraft/client/options/GameOptions;keyBindings:[Lnet/minecraft/client/options/KeyBinding;",
                     args = "array=get"))
  private KeyBinding getKeyBinding(KeyBinding[] keyBindings, int i)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
      return ((ControlsOptionsSubScreen) instance).getKeyBindings().get(i);
    return keyBindings[i];
  }

  @Redirect(method = "*",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/options/GameOptions;getOptionName(I)Ljava/lang/String;"))
  private String getOptionName(GameOptions options, int i)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
      return GameOptions.getKeyName(((ControlsOptionsSubScreen) instance).getKeyBindings().get(i).keyCode);
    return options.getOptionName(i);
  }

  @Redirect(method = "*",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;setKeyBinding(II)V"))
  private void setKeyBinding(GameOptions options, int i, int keyCode)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (!(instance instanceof ControlsOptionsSubScreen))
    {
      options.setKeyBinding(i, keyCode);
      return;
    }
    ((ControlsOptionsSubScreen) instance).getKeyBindings().get(i).keyCode = keyCode;
    options.save();
  }

  @Redirect(method = "render",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/options/GameOptions;translate(I)Ljava/lang/String;"))
  private String translate(GameOptions options, int i)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    LanguageManager lm = LanguageManager.getInstance();
    if (instance instanceof ControlsOptionsSubScreen)
      return lm.translate(((ControlsOptionsSubScreen) instance).getKeyBindings().get(i).name);
    return options.translate(i);
  }

  @ModifyConstant(method = "*", constant = @Constant(intValue = 160))
  private int longNameX(int constant)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen && ((ControlsOptionsSubScreen) instance).isLongNames())
      return 0;
    return constant;
  }

  @ModifyConstant(method = "*", constant = @Constant(intValue = 1))
  private int longNameY(int constant)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen && ((ControlsOptionsSubScreen) instance).isLongNames())
      return 0;
    return constant;
  }
}
