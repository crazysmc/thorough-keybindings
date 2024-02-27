package io.github.crazysmc.thrkbs.injector;

import org.spongepowered.asm.mixin.injection.invoke.ModifyConstantInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

public class ModifyIntIfEqualInjector extends ModifyConstantInjector
{
  public ModifyIntIfEqualInjector(InjectionInfo info)
  {
    super(info);
  }
}
