package io.github.crazysmc.thrkbs.gameoptions.mixin;

import io.github.crazysmc.thrkbs.core.api.HardcodedMapping;
import io.github.crazysmc.thrkbs.core.api.MappingRegistry;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.unmapped.C_7778778;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin
{
  @Shadow
  public KeyBinding[] keyBindings;

  @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V",
          at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/GameOptions;load()V"))
  private void onLoad(CallbackInfo ci) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
  {
    Set<? extends HardcodedMapping> mappings = MAPPING_REGISTRY.getRegisteredMappings();
    int i = keyBindings.length;
    keyBindings = Arrays.copyOf(keyBindings, i + mappings.size());
    for (HardcodedMapping mapping : mappings)
    {
      String name = mapping.getName();
      int keyCode = mapping.getKeyCode();
      C_7778778 keyMapping = new C_7778778(name, keyCode);
      keyBindings[i++] = keyMapping;
      /*
       * Work around the fact that gen1 calamus maps net/minecraft/client/options/KeyBinding
       * - in b1.7.3- as C_3543146
       * - in b1.8+   as C_7778778
       */
      MappingRegistry.class.getDeclaredMethod("registerMapping", int.class, String.class, C_7778778.class)
          .invoke(MAPPING_REGISTRY, keyCode, name, keyMapping);
    }
  }
}
