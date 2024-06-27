package io.github.crazysmc.thrkbs.gameoptions.mixin;

import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyBinding.class)
public interface KeyMappingAccessor
{
  @Accessor("ALL")
  static Map<String, KeyBinding> getAll()
  {
    throw new AssertionError();
  }

  @Accessor("CATEGORY_SORT_ORDER")
  static Map<String, Integer> getCategorySortOrder()
  {
    throw new AssertionError();
  }
}
