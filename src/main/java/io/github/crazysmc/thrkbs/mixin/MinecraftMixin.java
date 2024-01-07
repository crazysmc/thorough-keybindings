package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.snooper.SnooperPopulator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements Runnable, SnooperPopulator
{
  @ModifyConstant(method = "tick", constant = { @Constant(intValue = 59), @Constant(intValue = 63) })
  private int tick(int constant)
  {
    switch (constant)
    {
      case 59:
        return ThoroughKeybindings.hideGuiKey.keyCode;
      case 63:
        return ThoroughKeybindings.perspectiveKey.keyCode;
    }
    return constant;
  }

  @ModifyConstant(method = "tryTakeScreenshot", constant = @Constant(intValue = 60))
  private int tryTakeScreenshot(int constant)
  {
    return ThoroughKeybindings.screenshotKey.keyCode;
  }
}
