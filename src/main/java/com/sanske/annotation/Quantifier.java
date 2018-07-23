package com.sanske.annotation;

import java.lang.annotation.*;

/**
 * @author sanske
 * @date 2018/7/20 下午4:47
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Quantifier {
  String value() default "";
}
