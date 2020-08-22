package com.pay.typay.common.annotation;

import java.lang.annotation.*;

/**
 * 翻译
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TranslateKey {
     /**
      * 翻译列
      */
     String filedData() default "";

}
