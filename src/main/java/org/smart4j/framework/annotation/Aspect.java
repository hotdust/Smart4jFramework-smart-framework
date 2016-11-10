package org.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * @Author shijiapeng
 * @Date 2016/11/10 11:20
 * Created by shijiapeng on 2016/11/10.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    // value的值只能是注解类的子类
    Class<? extends Annotation> value();
}
