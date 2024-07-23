package io.github.crazysmc.thrkbs.remapkeys.mixin;

import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

import static io.github.crazysmc.thrkbs.core.ThoroughKeybindings.MAPPING_REGISTRY;
import static org.lwjgl.input.Keyboard.KEY_1;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @Shadow
  public LocalClientPlayerEntity player;

  @ModifyIntIfEqual(method = "tick",
                    slice = @Slice(from = @At(value = "INVOKE:LAST",
                                              target = "Lnet/minecraft/client/options/KeyBinding;click(I)V"),
                                   to = @At(value = "INVOKE:FIRST",
                                            target = "Lnet/minecraft/client/options/KeyBinding;consumeClick()Z")),
                    constant = @Constant)
  private int remapKeyConstants(int constant)
  {
    return MAPPING_REGISTRY.remapKeyCode(constant);
  }

  @ModifyConstant(method = "tick", constant = @Constant(intValue = 9, ordinal = 0))
  private int remapHotbarKeys(int constant)
  {
    for (int i = 0; i < 9; i++)
      if (Keyboard.getEventKey() == MAPPING_REGISTRY.remapKeyCode(KEY_1 + i))
        player.inventory.selectedSlot = i;
    return 0;
  }
}
