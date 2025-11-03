package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.List;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.debugCharts;
import static io.github.crazysmc.thrkbs.DynamicTextReplacer.keyBinding;
import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin
{
  @WrapOperation(
      method = "render",
      at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"),
      slice = @Slice(
          from = @At(
              value = "INVOKE:ONE",
              target = "Lnet/minecraft/client/gui/components/debug/DebugScreenEntryList;isF3Visible()Z"
          ),
          to = @At(
              value = "INVOKE:FIRST",
              target = "Lnet/minecraft/client/gui/components/DebugScreenOverlay;renderLines" +
                  "(Lnet/minecraft/client/gui/GuiGraphics;Ljava/util/List;Z)V"
          )
      )
  )
  private boolean render_drawGameInformation_add(List<String> instance, Object o, Operation<Boolean> original)
  {
    if (o instanceof String text)
    {
      String f3 = REGISTRY.get(DEBUG_INFO).getTranslatedKeyText();
      String f6 = REGISTRY.get(DEBUG_OPTIONS).getTranslatedKeyText();
      switch (text)
      {
        case "To edit: press F3 + F5. For help: press F3 + Q":
        case "To edit: press F3 + F6. For help: press F3 + Q":
          String q = REGISTRY.get(HELP).getTranslatedKeyText();
          o = "To edit: press %1$s + %2$s. For help: press %1$s + %3$s"
              .formatted(keyBinding(f3), keyBinding(f6), keyBinding(q));
          break;
        case "To edit: press F3 + F5":
        case "To edit: press F3 + F6":
          o = "To edit: press %s + %s".formatted(keyBinding(f3), keyBinding(f6));
          break;
        default:
          if (!text.startsWith("Debug charts: [F3+1] Profiler "))
            break;
          String chart1 = REGISTRY.get(CHARTS_PROFILER).getTranslatedKeyText();
          String chart2 = REGISTRY.get(CHARTS_FPS).getTranslatedKeyText();
          String chart3 = REGISTRY.get(CHARTS_NETWORK).getTranslatedKeyText();
          o = debugCharts(text, f3, chart1, chart2, chart3);
      }
    }
    return original.call(instance, o);
  }
}
