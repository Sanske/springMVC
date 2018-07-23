package com.sanske.annotation;

import java.lang.annotation.*;

/**
 * @author sanske
 * @date 2018/7/20 下午4:48
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
  String value() default  "";
}
