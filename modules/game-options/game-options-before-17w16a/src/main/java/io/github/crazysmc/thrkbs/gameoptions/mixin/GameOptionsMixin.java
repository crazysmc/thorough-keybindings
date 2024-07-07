package io.github.crazysmc.thrkbs.gameoptions.mixin;

import io.github.crazysmc.thrkbs.core.api.HardcodedMapping;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Set;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.LOGGER;
import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin
{
  @Shadow
  public KeyBinding[] keyBindings;

  @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V",
          at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;load()V"))
  private void onLoad(CallbackInfo ci)
  {
    Set<? extends HardcodedMapping> mappings = MAPPING_REGISTRY.getRegisteredMappings();
    int i = keyBindings.length;
    keyBindings = Arrays.copyOf(keyBindings, i + mappings.size());
    for (HardcodedMapping mapping : mappings)
    {
      String name = mapping.getName();
      int keyCode = mapping.getKeyCode();
      LOGGER.info("Add keybinding {}", name);
      KeyBinding keyMapping = new KeyBinding(name, keyCode, mapping.getCategory());
      KeyMappingAccessor.getAll().remove(keyMapping);
      keyBindings[i++] = keyMapping;
      MAPPING_REGISTRY.registerMapping(keyCode, keyMapping);
    }
    KeyBinding.resetMapping();
  }
}
