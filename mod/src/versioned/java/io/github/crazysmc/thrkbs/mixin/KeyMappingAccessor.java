package io.github.crazysmc.thrkbs.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyMapping.class)
public interface KeyMappingAccessor
{
  @Accessor("ALL")
  static Map<String, KeyMapping> getAll()
  {
    throw new AssertionError();
  }

  @Accessor("CATEGORY_SORT_ORDER")
  static Map<String, Integer> getCategorySortOrder()
  {
    throw new AssertionError();
  }

  @Accessor("key")
  InputConstants.Key getKey();
}
