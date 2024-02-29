package io.github.crazysmc.thrkbs.mixin.remap;

import com.google.common.collect.ImmutableMap;
import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

import java.util.Map;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @Unique
  private static final Map<String, Integer> DEBUG_HELP = ImmutableMap.<String, Integer>builder()
      .put("F3 + A = Reload chunks", Keyboard.KEY_A)
      .put("F3 + B = Show hitboxes", Keyboard.KEY_B)
      .put("F3 + D = Clear chat", Keyboard.KEY_D)
      .put("F3 + F = Cycle renderdistance (Shift to inverse)", Keyboard.KEY_F)
      .put("F3 + G = Show chunk boundaries", Keyboard.KEY_G)
      .put("F3 + H = Advanced tooltips", Keyboard.KEY_H)
      .put("F3 + N = Cycle creative <-> spectator", Keyboard.KEY_N)
      .put("F3 + P = Pause on lost focus", Keyboard.KEY_P)
      .put("F3 + Q = Show this list", Keyboard.KEY_Q)
      .put("F3 + T = Reload resourcepacks", Keyboard.KEY_T)
      .build();

  @Unique
  private int index;

  /* before 1.9 */
  @Group
  @ModifyIntIfEqual(method = "tick",
                    slice = @Slice(from = @At(value = "INVOKE:LAST",
                                              target = "Lnet/minecraft/client/options/KeyBinding;click(I)V"),
                                   to = @At(value = "INVOKE:FIRST",
                                            target = "Lnet/minecraft/client/options/KeyBinding;consumeClick()Z")),
                    constant = @Constant)
  private int remapKeyConstantTick(int constant)
  {
    if (constant != Keyboard.KEY_1)
      return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
    int i = index;
    index = i == 8 ? 0 : i + 1;
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant + i) - i;
  }

  /* since 1.9 */
  @Group
  @ModifyIntIfEqual(method = { "handleKeyboardEvents", "handleDebugKey" }, constant = @Constant)
  private int remapKeyConstantHandle(int constant)
  {
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
  }

  /* since 1.9 */
  @ModifyConstant(method = "handleDebugKey", constant = @Constant, require = 0)
  private String debugHelpText(String constant)
  {
    Integer original = DEBUG_HELP.get(constant);
    if (original == null)
      return constant;
    String f3 = GameOptions.getKeyName(CategorizedKeyBinding.getKeyCodeByOriginal(Keyboard.KEY_F3));
    String key = GameOptions.getKeyName(CategorizedKeyBinding.getKeyCodeByOriginal(original));
    return String.format("%s + %s%s", f3, key, constant.substring(6));
  }
}
