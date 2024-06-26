package io.github.crazysmc.thrkbs.core.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyBinding.class)
public interface KeyMappingAccessor
{
  @Accessor("ALL") // TODO
  static Map<String, KeyBinding> getAll()
  {
    throw new AssertionError();
  }

  @Accessor("CATEGORY_SORT_ORDER") // TODO
  static Map<String, Integer> getCategorySortOrder()
  {
    throw new AssertionError();
  }

  @Accessor("key")
  InputConstants.Key getKey();
}
