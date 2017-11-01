package com.item.demo.utils.databus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wuzongjie on 2017/11/1.
 * 自定义注解
 * 注解的学习:https://github.com/ShowJoy-com/showjoy-blog/issues/30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RegisterBus {
}
