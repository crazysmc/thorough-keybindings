package io.github.crazysmc.thrkbs;

import io.github.crazysmc.thrkbs.injector.ModifyIntIfEqualInjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({})
public class AnnotationProcessor extends AbstractProcessor
{
  static
  {
    InjectionInfo.register(ModifyIntIfEqualInjectionInfo.class);
  }

  @Override
  public SourceVersion getSupportedSourceVersion()
  {
    return SourceVersion.latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment)
  {
    return false;
  }
}
