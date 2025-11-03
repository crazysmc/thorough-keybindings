package io.github.crazysmc.thrkbs.version.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

import static io.github.crazysmc.thrkbs.DynamicTextReplacer.debugCharts;
import static io.github.crazysmc.thrkbs.HardcodedMapping.*;
import static io.github.crazysmc.thrkbs.version.KeyRemapping.REGISTRY;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin
{
  @WrapOperation(
      method = "render",
      at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z")
  )
  private boolean render_drawGameInformation_add(List<String> instance, Object o, Operation<Boolean> original)
  {
    String text = (String) o;
    if (!text.startsWith("Debug charts: [F3+1] Profiler "))
      return original.call(instance, o);
    String f3 = REGISTRY.get(DEBUG_INFO).getTranslatedKeyText();
    String chart1 = REGISTRY.get(CHARTS_PROFILER).getTranslatedKeyText();
    String chart2 = REGISTRY.get(CHARTS_FPS).getTranslatedKeyText();
    String chart3 = REGISTRY.get(CHARTS_NETWORK).getTranslatedKeyText();
    return original.call(instance, debugCharts(text, f3, chart1, chart2, chart3));
    // TODO
    // "To edit: press F3 + F6. For help: press F3 + Q"
    // "To edit: press F3 + F6"
  }
}
