package io.github.crazysmc.thrkbs.mixin.remap;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @Inject(method = "tick",
          slice = @Slice(from = @At(value = "INVOKE:LAST",
                                    target = "Lnet/minecraft/client/options/KeyBinding;click(I)V"),
                         to = @At(value = "INVOKE:FIRST",
                                  target = "Lnet/minecraft/client/options/KeyBinding;consumeClick()Z")),
          at = @At(value = "JUMP", opcode = Opcodes.IF_ICMPNE, shift = At.Shift.BY, by = -3),
          require = 0)
  private void setGotEventKey(CallbackInfo ci, @Share("gotEventKey") LocalBooleanRef gotEventKey)
  {
    gotEventKey.set(true);
  }

  @ModifyConstant(method = "tick",
                  slice = @Slice(from = @At(value = "INVOKE:LAST",
                                            target = "Lnet/minecraft/client/options/KeyBinding;click(I)V"),
                                 to = @At(value = "INVOKE:FIRST",
                                          target = "Lnet/minecraft/client/options/KeyBinding;consumeClick()Z")),
                  constant = @Constant)
  private int remapKeyConstant(int constant, @Share("gotEventKey") LocalBooleanRef gotEventKey,
                               @Share("index") LocalIntRef index)
  {
    if (!gotEventKey.get())
      return constant;
    gotEventKey.set(false);
    if (constant != Keyboard.KEY_1)
      return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
    int i = index.get();
    index.set(i == 8 ? 0 : i + 1);
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant + i) - i;
  }
}
