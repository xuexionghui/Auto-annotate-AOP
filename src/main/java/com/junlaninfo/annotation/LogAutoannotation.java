package com.junlaninfo.annotation;

import java.lang.annotation.*;

/**
 * Created by 辉 on 2020/3/17.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAutoannotation {

    /*
       接口描述
     */
    String  desc()  default  "";
}
