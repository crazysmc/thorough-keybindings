//$if <1.7.0
package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.ControlsOptionsSubScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//$if <1.3.0
//$ import net.minecraft.resource.language.I18n;
//$if >=1.3.0 <1.7.0
//$ import net.minecraft.client.resource.language.I18n;
//$if <1.7.0

@Mixin(ControlsOptionsScreen.class)
public abstract class ControlsOptionsScreenMixin extends Screen
{
  @Shadow
  protected String title;

  @Inject(method = "init", at = @At("RETURN"))
  private void changeTitle(CallbackInfo ci)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
      this.title = I18n.translate(((ControlsOptionsSubScreen) instance).getTitleKey());
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

  //$if <1.3.0

  @Redirect(method = "*",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/options/GameOptions;getKeyNameFromBinding(I)Ljava/lang/String;"))
  private String getKeyNameFromBinding(GameOptions options, int i)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
      return GameOptions.getKeyNameFromCode(((ControlsOptionsSubScreen) instance).getKeyBindings().get(i).keyCode);
    return options.getKeyNameFromBinding(i);
  }

  @Redirect(method = "render",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/options/GameOptions;getKeyBindingName(I)Ljava/lang/String;"))
  private String getKeyBindingName(GameOptions options, int i)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
      return I18n.translate(((ControlsOptionsSubScreen) instance).getKeyBindings().get(i).name);
    return options.getKeyBindingName(i);
  }

  //$if >=1.3.0 <1.7.0

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

  @Redirect(method = "render",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/options/GameOptions;translate(I)Ljava/lang/String;"))
  private String translate(GameOptions options, int i)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
      return I18n.translate(((ControlsOptionsSubScreen) instance).getKeyBindings().get(i).name);
    return options.translate(i);
  }

  //$if <1.7.0

  @Redirect(method = "*",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;setKeyBinding(II)V"))
  private void setKeyBinding(GameOptions options, int i, int keyCode)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
    {
      ((ControlsOptionsSubScreen) instance).getKeyBindings().get(i).keyCode = keyCode;
      options.save();
      return;
    }
    options.setKeyBinding(i, keyCode);
  }
}
