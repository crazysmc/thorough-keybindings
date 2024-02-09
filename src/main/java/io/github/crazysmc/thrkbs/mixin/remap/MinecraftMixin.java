package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import io.github.crazysmc.thrkbs.PotentialKeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.LocalClientPlayerEntity;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Minecraft.class)
@Debug(export = true)//TODO
public abstract class MinecraftMixin
{
  @Shadow
  public LocalClientPlayerEntity player;

  @ModifyConstant(method = "tick",
                  slice = @Slice(from = @At(value = "INVOKE",
                                            target = "Lorg/lwjgl/input/Keyboard;next()Z",
                                            remap = false),
                                 to = @At(value = "INVOKE:LAST",
                                          target = "Lnet/minecraft/client/Minecraft;selectProfilerChartSection(I)V")),
                  constant = {
                      @Constant(intValue = Keyboard.KEY_F1),
                      @Constant(intValue = Keyboard.KEY_F3),
                      @Constant(intValue = Keyboard.KEY_F5),
                      @Constant(intValue = Keyboard.KEY_F8),
                      @Constant(intValue = Keyboard.KEY_0),
                  })
  private int remapTickKeys(int constant)
  {
    return remapKeys(constant);
  }

  @ModifyConstant(method = "tick",
                  slice = @Slice(from = @At(value = "INVOKE",
                                            target = "Lnet/minecraft/client/gui/screen/Screen;handleKeyboard()V"),
                                 to = @At(value = "INVOKE",
                                          target = "Lnet/minecraft/client/Minecraft;openGameMenuScreen()V")),
                  constant = @Constant(intValue = Keyboard.KEY_ESCAPE))
  private int remapTickEscapeKey(int constant)
  {
    return remapKeys(constant);
  }

  @ModifyConstant(method = "tryTakeScreenshot", constant = @Constant(intValue = Keyboard.KEY_F2))
  private int remapScreenshotKey(int constant)
  {
    return remapKeys(constant);
  }

  @ModifyConstant(method = "runGame", constant = @Constant(intValue = Keyboard.KEY_F7))
  private int remapDelayDisplayUpdateKey(int constant)
  {
    return remapKeys(constant);
  }

  @Unique
  private int remapKeys(int constant)
  {
    CategorizedKeyBinding keyBinding = CategorizedKeyBinding.getByOriginal(constant);
    return keyBinding == null ? constant : keyBinding.keyCode;
  }

  @Shadow
  protected abstract void selectProfilerChartSection(int i);

  @ModifyConstant(method = "tick",
                  slice = @Slice(from = @At(value = "INVOKE",
                                            target = "Lnet/minecraft/client/Minecraft;selectProfilerChartSection(I)V"),
                                 to = @At(value = "INVOKE:LAST",
                                          target = "Lnet/minecraft/client/Minecraft;selectProfilerChartSection(I)V")),
                  constant = @Constant(intValue = 9))
  private int remapProfilerChartKeys(int constant)
  {
    for (int i = 0; i < 9; i++)
      if (Keyboard.getEventKey() == PotentialKeyBinding.PROFILER_CHART[i].getKeyBinding().keyCode)
        selectProfilerChartSection(i + 1);
    return 0;
  }

  @ModifyConstant(method = "tick",
                  slice = @Slice(to = @At(value = "INVOKE",
                                          target = "Lnet/minecraft/client/Minecraft;selectProfilerChartSection(I)V")),
                  constant = @Constant(intValue = 9))
  private int remapHotbarKeys(int constant)
  {
    for (int i = 0; i < 9; i++)
      if (Keyboard.getEventKey() == PotentialKeyBinding.HOTBAR[i].getKeyBinding().keyCode)
        player.inventory.selectedSlot = i;
    return 0;
  }
}
