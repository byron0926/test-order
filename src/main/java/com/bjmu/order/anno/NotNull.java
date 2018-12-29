package com.bjmu.order.anno;


import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface NotNull {
    String value() default "改字段不能为空";
}
