package com.qiya.middletier.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qiya.middletier.bizenum.CmsCatergoryEnum;

/**
 * Created by qiyalm on 16/7/29.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageViewAnnotation {
    /**
     * 业务类型枚举
     * @return
     */
    CmsCatergoryEnum value() ;

    /**
     * id的key
     * @return
     */
    String prarmkey();
}
