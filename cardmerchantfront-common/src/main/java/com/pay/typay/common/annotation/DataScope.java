package com.pay.typay.common.annotation;



import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 *
 * @author js-oswald
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
}
