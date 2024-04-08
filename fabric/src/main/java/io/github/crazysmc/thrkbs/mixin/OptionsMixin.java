package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.CustomKeyMapping;
import io.github.crazysmc.thrkbs.PotentialKeyBinding;
import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(Options.class)
public abstract class OptionsMixin
{
  @Shadow
  @Final
  @Mutable
  public KeyMapping[] keyMappings;

  @Inject(method = "load", at = @At("HEAD"))
  private void appendKeyMappings(CallbackInfo ci)
  {
    List<PotentialKeyBinding> list = PotentialKeyBinding.foundBindings();
    int length = keyMappings.length;
    int addLength = list.size();
    ThoroughKeybindings.LOGGER.info("Found {} keybindings to remap", addLength);
    keyMappings = Arrays.copyOf(keyMappings, length + addLength);
    for (PotentialKeyBinding binding : list)
    {
      String name = binding.getName();
      ThoroughKeybindings.LOGGER.info("Add keybinding {}", name);
      keyMappings[length++] = new CustomKeyMapping(name, binding.getKeyCode(), binding.getCategory());
    }
  }
}
