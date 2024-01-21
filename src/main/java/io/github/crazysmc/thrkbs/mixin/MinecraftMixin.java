package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import net.minecraft.snooper.SnooperPopulator;
import net.minecraft.util.BlockableEventLoop;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements BlockableEventLoop, SnooperPopulator
{
  @Shadow
  protected abstract void selectProfilerChartSection(int section);

  @ModifyConstant(method = "handleKeyboardEvents", constant = {
      @Constant(intValue = Keyboard.KEY_ESCAPE, ordinal = 1),
      @Constant(intValue = Keyboard.KEY_0),
      @Constant(intValue = Keyboard.KEY_F1),
      @Constant(intValue = Keyboard.KEY_F3),
      @Constant(intValue = Keyboard.KEY_F4),
      @Constant(intValue = Keyboard.KEY_C),
  })
  private int handleKeyboardEventsRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }

  @ModifyConstant(method = "handleDebugKey", constant = {
      @Constant(intValue = Keyboard.KEY_T),
      @Constant(intValue = Keyboard.KEY_A),
      @Constant(intValue = Keyboard.KEY_F),
      @Constant(intValue = Keyboard.KEY_P),
      @Constant(intValue = Keyboard.KEY_H),
      @Constant(intValue = Keyboard.KEY_B),
      @Constant(intValue = Keyboard.KEY_D),
      @Constant(intValue = Keyboard.KEY_Q),
      @Constant(intValue = Keyboard.KEY_N),
  })
  private int handleDebugKeyRemap(int constant)
  {
    return ThoroughKeybindings.getRemap(constant);
  }

  @ModifyConstant(method = "handleDebugKey", constant = {
      @Constant(stringValue = "F3 + A = Reload chunks"),
      @Constant(stringValue = "F3 + B = Show hitboxes"),
      @Constant(stringValue = "F3 + D = Clear chat"),
      @Constant(stringValue = "F3 + F = Cycle renderdistance (Shift to inverse)"),
      @Constant(stringValue = "F3 + H = Advanced tooltips"),
      @Constant(stringValue = "F3 + N = Cycle creative <-> spectator"),
      @Constant(stringValue = "F3 + P = Pause on lost focus"),
      @Constant(stringValue = "F3 + Q = Show this list"),
      @Constant(stringValue = "F3 + T = Reload resourcepacks")
  })
  private String handleDebugKeyHelpText(String constant)
  {
    int original = Keyboard.getKeyIndex(String.valueOf(constant.charAt(5)));
    String replacement = String.format("%s + %s", GameOptions.getKeyName(ThoroughKeybindings.getRemap(Keyboard.KEY_F3)),
                                       GameOptions.getKeyName(ThoroughKeybindings.getRemap(original)));
    return constant.replaceFirst("F3 \\+ .", replacement);
  }

  @ModifyConstant(method = "handleKeyboardEvents", constant = @Constant(intValue = 9, ordinal = 0))
  private int handleKeyboardEventsProfilerChart(int constant)
  {
    for (int i = 0; i < 9; i++)
      if (Keyboard.getEventKey() == ThoroughKeybindings.getProfilerRemap(i))
        selectProfilerChartSection(i + 1);
    return 0;
  }
}
