package com.pay.typay.common.annotation;

import java.lang.annotation.*;

/**
 * 翻译
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Translate {
     /**
      * 翻译列
      */
     String filed() default "";

     /**
      * 翻译数据
      */
     String  data() default "";
}
