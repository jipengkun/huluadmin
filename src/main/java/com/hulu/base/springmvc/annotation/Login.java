package com.hulu.base.springmvc.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * Created by huangyiwei on 15/10/23.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
    enum ResultTypeEnum {
        //整页刷新
        page,
        //json数据
        json
    }

    ResultTypeEnum value() default ResultTypeEnum.page;
}
