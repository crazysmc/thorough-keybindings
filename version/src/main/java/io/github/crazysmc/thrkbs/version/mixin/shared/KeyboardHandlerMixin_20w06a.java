package io.github.crazysmc.thrkbs.version.mixin.shared;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.crazysmc.thrkbs.HardcodedMapping;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.EnumSet;

import static io.github.crazysmc.thrkbs.HardcodedMapping.PROFILER_0;
import static io.github.crazysmc.thrkbs.HardcodedMapping.PROFILER_9;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin_20w06a
{
  /*
   * profiler switches change from a loop to a range check in 20w06a
   */
  @Definition(id = "key", local = @Local(type = int.class, argsOnly = true, ordinal = 0))
  @Expression("@(key) >= '0'")
  @ModifyVariable(method = "keyPress", at = @At("MIXINEXTRAS:EXPRESSION"), argsOnly = true, ordinal = 0)
  private int keyPress_intDigit(int key, long window, int _key, int scancode)
  {
    for (HardcodedMapping mapping : EnumSet.range(PROFILER_0, PROFILER_9))
      if (REGISTRY.get(mapping).matches(key, scancode))
        return mapping.getKeyCode();
    return GLFW_KEY_UNKNOWN;
  }
}
