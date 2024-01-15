package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.snooper.SnooperPopulator;
import org.lwjgl.input.Keyboard;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements Runnable, SnooperPopulator
{
  @Shadow
  protected abstract void selectProfilerChartSection(int section);

  @ModifyConstant(method = "tick",
                  slice = @Slice(from = @At(value = "INVOKE",
                                            target = "Lorg/lwjgl/input/Keyboard;next()Z",
                                            remap = false)),
                  constant = {
                      @Constant(intValue = Keyboard.KEY_ESCAPE, ordinal = 0),
                      @Constant(intValue = Keyboard.KEY_0),
                      @Constant(intValue = Keyboard.KEY_T),
                      @Constant(intValue = Keyboard.KEY_A, ordinal = 0),
                      @Constant(intValue = Keyboard.KEY_S),
                      @Constant(intValue = Keyboard.KEY_F),
                      @Constant(intValue = Keyboard.KEY_LSHIFT),
                      @Constant(intValue = Keyboard.KEY_RSHIFT),
                      @Constant(intValue = Keyboard.KEY_F1),
                      @Constant(intValue = Keyboard.KEY_F3),
                      @Constant(intValue = Keyboard.KEY_F11),
                      @Constant(intValue = Keyboard.KEY_P),
                      @Constant(intValue = Keyboard.KEY_H),
                      @Constant(intValue = Keyboard.KEY_C),
                      @Constant(intValue = Keyboard.KEY_B),
                  })
  private int tickRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }

  @ModifyConstant(method = "tick",
                  slice = @Slice(from = @At(value = "FIELD",
                                            opcode = Opcodes.PUTFIELD,
                                            target = "Lnet/minecraft/client/options/GameOptions;smoothCamera:Z")),
                  constant = @Constant(intValue = 9, ordinal = 0))
  private int tickProfilerChart(int constant)
  {
    for (int i = 0; i < 9; i++)
      if (Keyboard.getEventKey() == ThoroughKeybindings.getProfilerRemap(i))
        selectProfilerChartSection(i + 1);
    return 0;
  }
}
