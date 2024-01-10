package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.snooper.SnooperPopulator;
import org.lwjgl.input.Keyboard;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements Runnable, SnooperPopulator
{
  @Shadow
  public LocalClientPlayerEntity player;

  @Shadow
  protected abstract void selectProfilerChartSection(int section);

  @ModifyConstant(method = "tick", constant = {
      @Constant(intValue = Keyboard.KEY_ESCAPE, ordinal = 3),
      @Constant(intValue = Keyboard.KEY_0),
      @Constant(intValue = Keyboard.KEY_T),
      @Constant(intValue = Keyboard.KEY_A),
      @Constant(intValue = Keyboard.KEY_S),
      @Constant(intValue = Keyboard.KEY_F1),
      @Constant(intValue = Keyboard.KEY_F3),
      @Constant(intValue = Keyboard.KEY_F5),
      @Constant(intValue = Keyboard.KEY_F8),
      @Constant(intValue = Keyboard.KEY_F11),
  })
  private int tick(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }

  @Inject(method = "tick", at = @At(value = "CONSTANT", args = "intValue=9", ordinal = 0, shift = At.Shift.BY, by = -2))
  private void tickHotbar(CallbackInfo ci)
  {
    for (int i = 0; i < 9; i++)
      if (Keyboard.getEventKey() == ThoroughKeybindings.getHotbarRemap(i))
        player.inventory.selectedSlot = i;
  }

  @Redirect(method = "tick",
            at = @At(value = "FIELD",
                     target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I",
                     opcode = Opcodes.PUTFIELD))
  private void tick(PlayerInventory instance, int value)
  {
  }

  @Inject(method = "tick", at = @At(value = "CONSTANT", args = "intValue=9", ordinal = 1, shift = At.Shift.BY, by = -2))
  private void tickProfiler(CallbackInfo ci)
  {
    for (int i = 0; i < 9; i++)
      if (Keyboard.getEventKey() == ThoroughKeybindings.getProfilerRemap(i))
        selectProfilerChartSection(i + 1);
  }

  @Redirect(method = "tick",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/client/Minecraft;selectProfilerChartSection(I)V",
                     ordinal = 1))
  private void tick(Minecraft instance, int i)
  {
  }

  @ModifyConstant(method = "tryTakeScreenshot", constant = @Constant(intValue = Keyboard.KEY_F2))
  private int tryTakeScreenshot(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }
}
