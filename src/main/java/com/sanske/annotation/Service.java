package com.sanske.annotation;

import java.lang.annotation.*;

/**
 * @author sanske
 * @date 2018/7/20 下午4:48
 **/

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
  String value() default "";
}
