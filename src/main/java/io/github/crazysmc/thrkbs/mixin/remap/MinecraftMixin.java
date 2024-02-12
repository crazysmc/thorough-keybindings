package io.github.crazysmc.thrkbs.mixin.remap;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @ModifyExpressionValue(method = "tick",
                         slice = @Slice(from = @At(value = "INVOKE",
                                                   target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z",
                                                   remap = false)),
                         at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", remap = false))
  private int remapEventKey(int eventKey)
  {
    return CategorizedKeyBinding.getOriginalByKeyCode(eventKey);
  }

  @ModifyArg(method = "*",
             at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z", remap = false))
  private int remapKeyDownArgument(int key)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(key);
  }
}
