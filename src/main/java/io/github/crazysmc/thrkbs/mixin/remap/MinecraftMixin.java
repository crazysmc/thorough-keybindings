package io.github.crazysmc.thrkbs.mixin.remap;

import io.github.crazysmc.thrkbs.CategorizedKeyBinding;
import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqual;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.HashMap;
import java.util.Map;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin
{
  @Unique
  private static final Map<String, Integer> DEBUG_HELP = new HashMap<>(16);

  static
  {
    DEBUG_HELP.put("F3 + A = Reload chunks", Keyboard.KEY_A);
    DEBUG_HELP.put("F3 + B = Show hitboxes", Keyboard.KEY_B);
    DEBUG_HELP.put("F3 + D = Clear chat", Keyboard.KEY_D);
    DEBUG_HELP.put("F3 + F = Cycle renderdistance (Shift to inverse)", Keyboard.KEY_F);
    DEBUG_HELP.put("F3 + H = Advanced tooltips", Keyboard.KEY_H);
    DEBUG_HELP.put("F3 + N = Cycle creative <-> spectator", Keyboard.KEY_N);
    DEBUG_HELP.put("F3 + P = Pause on lost focus", Keyboard.KEY_P);
    DEBUG_HELP.put("F3 + Q = Show this list", Keyboard.KEY_Q);
    DEBUG_HELP.put("F3 + T = Reload resourcepacks", Keyboard.KEY_T);
  }

  @Unique
  private int index;

  /* before 1.9 */
  @ModifyIntIfEqual(method = "tick",
                    slice = @Slice(from = @At(value = "INVOKE:LAST",
                                              target = "Lnet/minecraft/client/options/KeyBinding;click(I)V"),
                                   to = @At(value = "INVOKE:FIRST",
                                            target = "Lnet/minecraft/client/options/KeyBinding;consumeClick()Z")),
                    constant = @Constant,
                    require = 0)
  private int remapKeyConstant(int constant)
  {
    if (constant != Keyboard.KEY_1)
      return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
    int i = index;
    index = i == 8 ? 0 : i + 1;
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant + i) - i;
  }

/*  @Unique
  private boolean jumpIfNotEqual = false;

  @Inject(method = "tick",
          slice = @Slice(from = @At(value = "INVOKE:LAST",
                                    target = "Lnet/minecraft/client/options/KeyBinding;click(I)V"),
                         to = @At(value = "INVOKE:FIRST",
                                  target = "Lnet/minecraft/client/options/KeyBinding;consumeClick()Z")),
          at = @At(value = "JUMP", opcode = Opcodes.IF_ICMPNE, shift = At.Shift.BY, by = -3),
          require = 0)
  private void setJumpIfNotEqual(CallbackInfo ci)
  {
    jumpIfNotEqual = true;
  }

  @Inject(method = "handleKeyboardEvents",
          at = @At(value = "JUMP", opcode = Opcodes.IF_ICMPNE, shift = At.Shift.BY, by = -3),
          require = 0)
  private void setJumpIfNotEqual2(CallbackInfo ci)
  {
    jumpIfNotEqual = true;
  }

  @Inject(method = "handleDebugKey",
          at = @At(value = "JUMP", opcode = Opcodes.IF_ICMPNE, shift = At.Shift.BY, by = -3),
          require = 0)
  private void setJumpIfNotEqual(int key, CallbackInfoReturnable<Boolean> cir)
  {
    jumpIfNotEqual = true;
  }

  @ModifyConstant(method = "tick",
                  slice = @Slice(from = @At(value = "INVOKE:LAST",
                                            target = "Lnet/minecraft/client/options/KeyBinding;click(I)V"),
                                 to = @At(value = "INVOKE:FIRST",
                                          target = "Lnet/minecraft/client/options/KeyBinding;consumeClick()Z")),
                  constant = @Constant,
                  require = 0)
  private int remapKeyConstant(int constant)
  {
    if (!jumpIfNotEqual)
      return constant;
    jumpIfNotEqual = false;
    if (constant != Keyboard.KEY_1)
      return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
    int i = index;
    index = i == 8 ? 0 : i + 1;
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant + i) - i;
  }

  @ModifyConstant(method = { "handleKeyboardEvents", "handleDebugKey" }, constant = @Constant, require = 0)
  private int remapKeyConstant2(int constant)
  {
    if (!jumpIfNotEqual)
      return constant;
    jumpIfNotEqual = false;
    return CategorizedKeyBinding.getKeyCodeByOriginal(constant);
  }*/

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
