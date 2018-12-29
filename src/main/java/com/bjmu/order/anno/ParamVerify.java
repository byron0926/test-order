package com.bjmu.order.anno;

import java.lang.annotation.*;

/*
* bean校验注解标记
* */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ParamVerify {
}
