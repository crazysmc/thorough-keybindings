package io.github.crazysmc.thrkbs.version.mixin;

import io.github.crazysmc.thrkbs.MappingCategory;
import net.minecraft.client.KeyMapping.Category;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Locale;

import static io.github.crazysmc.thrkbs.MappingCategory.*;

@Mixin(Category.class)
public abstract class CategoryMixin
{
  @Shadow
  @Final
  @Mutable
  private static Category[] $VALUES;

  @Invoker("<init>")
  protected static Category constructor(String name, int ordinal, String descriptionId)
  {
    throw new AssertionError();
  }

  @Inject(method = "<clinit>", at = @At("RETURN"))
  private static void classInit(CallbackInfo ci)
  {
    MappingCategory[] categories = { DEBUG, MODIFIER, PROFILER };
    int length = $VALUES.length;
    $VALUES = Arrays.copyOf($VALUES, length + categories.length);
    for (int i = 0; i < categories.length; i++)
      $VALUES[length + i] = constructor(categories[i].getType().toUpperCase(Locale.ROOT), length + i,
                                        categories[i].getId());
  }
}
