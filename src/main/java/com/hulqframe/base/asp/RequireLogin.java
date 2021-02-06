package com.hulqframe.base.asp;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireLogin {
    boolean isPermission() default false;//是否进行权限验证
}
