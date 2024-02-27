package io.github.crazysmc.thrkbs.injector;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.AnnotationType;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo.HandlerPrefix;
import org.spongepowered.asm.mixin.injection.struct.ModifyConstantInjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

import java.util.List;

@AnnotationType(ModifyIntIfEqual.class)
@HandlerPrefix("intIfEqual")
public class ModifyIntIfEqualInjectionInfo extends ModifyConstantInjectionInfo
{
  public ModifyIntIfEqualInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation)
  {
    super(mixin, method, annotation);
  }

  @Override
  protected void parseInjectionPoints(List<AnnotationNode> ats)
  {
    Type returnType = Type.getReturnType(method.desc);
    for (AnnotationNode at : ats)
      injectionPoints.add(new BeforeIntIfEqual(getMixin(), at, returnType.getDescriptor()));
  }
}
