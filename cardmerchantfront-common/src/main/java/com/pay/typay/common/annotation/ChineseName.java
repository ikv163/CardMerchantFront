package com.pay.typay.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义属性中文映射注解
 *
 * @author js-warren
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChineseName {

    //实体属性中文映射
    String value() default "";
}
