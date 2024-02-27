package io.github.crazysmc.thrkbs.injector;

import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Like {@link ModifyConstant} but for integers used in the construct
 * <code>if (var == constant) { ... }</code>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyIntIfEqual
{
  String[] method() default {};

  Desc[] target() default {};

  Slice[] slice() default {};

  Constant[] constant() default {};

  boolean remap() default true;

  int require() default -1;

  int expect() default 1;

  int allow() default -1;

  String constraints() default "";
}
