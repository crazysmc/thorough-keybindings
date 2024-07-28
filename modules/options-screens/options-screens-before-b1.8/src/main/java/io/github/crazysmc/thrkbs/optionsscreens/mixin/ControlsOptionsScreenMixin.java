package io.github.crazysmc.thrkbs.optionsscreens.mixin;

import io.github.crazysmc.thrkbs.optionsscreens.ControlsOptionsSubScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.resource.language.I18n;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
      title = I18n.translate(((ControlsOptionsSubScreen) instance).getCategory());
  }

  @Redirect(method = { "init", "buttonClicked", "render" },
            at = @At(value = "FIELD",
                     target = "Lnet/minecraft/client/options/GameOptions;keyBindings:[Lnet/minecraft/client/options/KeyBinding;",
                     args = "array=length"))
  private int getKeyBindingsLength(KeyBinding[] keyBindings)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
      return ((ControlsOptionsSubScreen) instance).getKeyMappings().size();
    return keyBindings.length;
  }

  @Redirect(method = { "init", "buttonClicked", "keyPressed", "render" },
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/options/GameOptions;getKeyNameFromBinding(I)Ljava/lang/String;"))
  private String getOptionName(GameOptions options, int i)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
    {
      int keyCode = ((ControlsOptionsSubScreen) instance).getKeyMappings().get(i).keyCode;
      return keyCode < 0 ? I18n.translate("key.mouseButton", keyCode + 101) : Keyboard.getKeyName(keyCode);
    }
    return options.getKeyNameFromBinding(i);
  }

  @Redirect(method = "render",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/options/GameOptions;getKeyBindingName(I)Ljava/lang/String;"))
  private String translate(GameOptions options, int i)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
      return I18n.translate(((ControlsOptionsSubScreen) instance).getKeyMappings().get(i).name);
    return options.getKeyBindingName(i);
  }

  @Redirect(method = "keyPressed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;setKeyBinding(II)V"))
  private void setKeyBinding(GameOptions options, int i, int keyCode)
  {
    ControlsOptionsScreen instance = (ControlsOptionsScreen) (Object) this;
    if (instance instanceof ControlsOptionsSubScreen)
    {
      ((ControlsOptionsSubScreen) instance).getKeyMappings().get(i).keyCode = keyCode;
      options.save();
      return;
    }
    options.setKeyBinding(i, keyCode);
  }
}
