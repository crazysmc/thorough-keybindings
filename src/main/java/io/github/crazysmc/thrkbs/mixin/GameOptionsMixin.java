package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.PotentialKeyBinding;
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
  public KeyBinding[] keyBindings;

  @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V",
          at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;load()V"))
  private void registerKeybindings(CallbackInfo ci)
  {
    Set<String> keyNames = Arrays.stream(keyBindings).map(keyBinding -> keyBinding.name).collect(Collectors.toSet());
    List<KeyBinding> addKeyBindings = new ArrayList<>();
    PotentialKeyBinding.register(keyNames, addKeyBindings::add);
    int originalLength = keyBindings.length;
    int addLength = addKeyBindings.size();
    keyBindings = Arrays.copyOf(keyBindings, originalLength + addLength);
    IntStream.range(0, addLength).forEach(i -> keyBindings[originalLength + i] = addKeyBindings.get(i));
  }
}
