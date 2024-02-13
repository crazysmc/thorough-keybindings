package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @Unique
  private boolean gotEventKey = false;
  @Unique
  private int i = 0;

  @Inject(method = "tick",
          slice = @Slice(from = @At(value = "INVOKE",
                                    target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z",
                                    remap = false)),
          at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", remap = false))
  private void setGotEventKey(CallbackInfo ci)
  {
    gotEventKey = true;
  }

  @ModifyConstant(method = "tick",
                  slice = @Slice(from = @At(value = "INVOKE",
                                            target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z",
                                            remap = false)))
  private int remapKeyConstant(int constant)
  {
    if (!gotEventKey)
      return constant;
    gotEventKey = false;
    if (constant != Keyboard.KEY_1)
      return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
    int remap = CategorizedKeyBinding.getKeyCodeByOriginal(constant + i) - i;
    if (++i == 9)
      i = 0;
    return remap;
  }

  @ModifyArg(method = "*",
             at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z", remap = false))
  private int remapKeyDownArgument(int key)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(key);
  }
}
