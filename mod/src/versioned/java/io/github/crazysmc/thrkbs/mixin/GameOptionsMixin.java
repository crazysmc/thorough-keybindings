//$if <1.3.0
package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.CustomKeyBinding;
import io.github.crazysmc.thrkbs.PotentialKeyBinding;
import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin
{
  @Shadow
  public KeyBinding[] keyBindings;

  @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V",
          at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;load()V"))
  private void onLoad(CallbackInfo ci)
  {
    List<PotentialKeyBinding> list = PotentialKeyBinding.getFoundBindings().collect(Collectors.toList());
    int i = keyBindings.length;
    keyBindings = Arrays.copyOf(keyBindings, i + list.size());
    for (PotentialKeyBinding binding : list)
    {
      String name = binding.getName();
      ThoroughKeybindings.LOGGER.info("Add keybinding {}", name);
      keyBindings[i++] = new CustomKeyBinding(name, binding.getKeyCode(), binding.getCategory());
    }
    //$if >=1.0.0-beta.1.8.0.z <1.3.0
    KeyBinding.updateKeyCodeMap();
    //$if <1.3.0
  }
}
