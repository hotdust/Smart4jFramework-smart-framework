package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 10:33
 * Created by shijiapeng on 2016/11/4.
 */
public class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> cls) {
        Object rtnInstance;
        try {
            rtnInstance = cls.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("new instance error", e);
            return new RuntimeException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error("new instance error", e);
            return new RuntimeException(e);
        }
        return rtnInstance;
    }

    public static void setField(Object obj, Field field, Object fieldValue) {
        try {
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field error", e);
            throw new RuntimeException(e);
        }
    }
}
