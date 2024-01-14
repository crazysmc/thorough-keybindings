package io.github.crazysmc.thrkbs.mixin;

import net.ornithemc.osl.resource.loader.api.ModTexturePack;
import net.ornithemc.osl.resource.loader.impl.BuiltInModTexturePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = BuiltInModTexturePack.class, remap = false)
public abstract class BuiltInModTexturePackMixin implements ModTexturePack
{
  @ModifyVariable(method = "getPath", at = @At("HEAD"), argsOnly = true)
  private String getPath(String location)
  {
    return "/pack.png".equals(location) || "/pack.txt".equals(location) ? location : location.replaceFirst("^/", "");
  }
}
