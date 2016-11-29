package org.smart4j.framework.helper;

import org.apache.commons.collections.map.HashedMap;
import org.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * 功能：根据Class文件，成生相应的Instance，然后保存成一个Class和Instance的键值对的Map。
 * 例如：LoginController.class和LoginController的实例
 *
 *
 * @Author shijiapeng
 * @Date 2016/11/4 10:26
 * Created by shijiapeng on 2016/11/4.
 */
public class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取 Bean 映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取 Bean 实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class: " + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * 设置 Bean 实例
     * 如果Bean实例已经存在，就替换掉
     * （在本书主，这个方法创建原因是为了AOP功能。当AOP的代理对象实例创建完后，用代理对象实例替换掉原来的实例）
     *
     * @param cls Class类
     * @param obj 要替换的实例
     * @modified 2016/11/29 上午9:58
     */
    public static void setBean(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }
}
