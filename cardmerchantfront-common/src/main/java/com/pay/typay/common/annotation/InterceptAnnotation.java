package com.pay.typay.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配合MySqlInterceptor实现局部拦截
 * */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptAnnotation {
    //false 需要拦截掉，不执行
    boolean flag() default  true;

}