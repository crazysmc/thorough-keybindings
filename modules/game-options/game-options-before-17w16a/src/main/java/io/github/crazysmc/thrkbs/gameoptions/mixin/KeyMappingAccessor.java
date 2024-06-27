package io.github.crazysmc.thrkbs.gameoptions.mixin;

import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(KeyBinding.class)
public interface KeyMappingAccessor
{
  @Accessor("ALL")
  static List<KeyBinding> getAll()
  {
    throw new AssertionError();
  }
}
