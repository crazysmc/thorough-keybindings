package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.KeyMapping;
import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin
{
  @Shadow
  private KeyBinding[] keyBindings;

  @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V",
          at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;load()V"))
  private void registerKeybindings(CallbackInfo ci)
  {
    KeyMapping[] mappings = KeyMapping.values();
    Set<String> keyNames = Arrays.stream(keyBindings).map(keyBinding -> keyBinding.name).collect(Collectors.toSet());
    List<KeyBinding> addKeyBindings = new ArrayList<>();
    for (KeyMapping mapping : mappings)
      if (!keyNames.contains(mapping.getName()))
      {
        KeyBinding binding = new KeyBinding(mapping.getName(), mapping.getOriginal());
        addKeyBindings.add(binding);
        ThoroughKeybindings.putMapping(binding, mapping);
      }
    int originalLength = keyBindings.length;
    int addLength = addKeyBindings.size();
    keyBindings = Arrays.copyOf(keyBindings, originalLength + addLength);
    IntStream.range(0, addLength).forEach(i -> keyBindings[originalLength + i] = addKeyBindings.get(i));
  }
}
