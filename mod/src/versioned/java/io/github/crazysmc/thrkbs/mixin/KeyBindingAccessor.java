package io.github.crazysmc.thrkbs.mixin;

import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor
{
  @Accessor("keysById")
  static Map<String, KeyBinding> getAll()
  {
    throw new AssertionError();
  }

  @Accessor("categoryOrderMap")
  static Map<String, Integer> getCategorySortOrder()
  {
    throw new AssertionError();
  }

  @Accessor("keyCode")
  InputUtil.KeyCode getKey();
}
