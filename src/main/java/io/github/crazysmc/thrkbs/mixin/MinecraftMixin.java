package io.github.crazysmc.thrkbs.mixin;

import io.github.crazysmc.thrkbs.ThoroughKeybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.snooper.SnooperPopulator;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements Runnable, SnooperPopulator
{
  @ModifyConstant(method = "tick",
                  constant = { @Constant(intValue = Keyboard.KEY_F1), @Constant(intValue = Keyboard.KEY_F5) })
  private int tick(int constant)
  {
    switch (constant)
    {
      case Keyboard.KEY_F1:
        return ThoroughKeybindings.hideGuiKey.keyCode;
      case Keyboard.KEY_F5:
        return ThoroughKeybindings.perspectiveKey.keyCode;
    }
    return constant;
  }

  @ModifyConstant(method = "tryTakeScreenshot", constant = @Constant(intValue = Keyboard.KEY_F2))
  private int tryTakeScreenshot(int constant)
  {
    return ThoroughKeybindings.screenshotKey.keyCode;
  }
}
