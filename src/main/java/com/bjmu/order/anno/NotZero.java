package com.bjmu.order.anno;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NotZero {
    String value() default "该字段不能为0";
}
