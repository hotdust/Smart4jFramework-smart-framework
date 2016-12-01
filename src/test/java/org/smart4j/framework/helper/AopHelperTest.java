package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.logic.ControllerAspect;
import org.smart4j.framework.logic.ControllerClass;
import org.smart4j.framework.proxy.Proxy;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.testng.AssertJUnit.*;

/**
 * Created by shijiapeng on 16/11/29.
 */
public class AopHelperTest {

    @Test
    public void testStaticBlock() {

    }

    @Test
    public void testCreateProxyMap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 调用测试方法
        Method method = AopHelper.class.getDeclaredMethod("createProxyMap");
        method.setAccessible(true);
        Map<Class<?>, Set<Class<?>>> invokeResult = (Map<Class<?>, Set<Class<?>>>) method.invoke(null);

       // 判断结果集合不为空
        assertFalse(invokeResult.isEmpty());

        for (Map.Entry<Class<?>, Set<Class<?>>> entry : invokeResult.entrySet()) {
            // 判断key是不是带有Aspect注解的类
            Class<?> key = entry.getKey();
            assertTrue(key.isAnnotationPresent(Aspect.class));

            // values里的类，是不是带有controller注解的类
            Set<Class<?>> values = entry.getValue();
            values.forEach(cls -> {
                if (cls.isAnnotationPresent(Controller.class)) {
                    assertTrue(true);
                } else {
                    assertTrue(false);
                }
                System.out.println("----------------" + cls.getSimpleName());
            });
        }

    }

    @Test
    public void testCreateTargetClassSet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 取得ControllerAspect的注解内容
        Aspect aspect = ControllerAspect.class.getAnnotation(Aspect.class);
        // 调用测试方法
        Method method = AopHelper.class.getDeclaredMethod("createTargetClassSet", Aspect.class);
        method.setAccessible(true);
        Set<Class<?>> result = (Set<Class<?>>)method.invoke(null, aspect);

        // 判断返回值是不是和我们想的一样
        result.forEach(cls -> assertTrue(cls.equals(ControllerClass.class)));
    }

    @Test
    public void testCreateTargetMap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 生成要测试方法的参数
        Method paramGenerationMethod = AopHelper.class.getDeclaredMethod("createProxyMap");
        paramGenerationMethod.setAccessible(true);
        Map<Class<?>, Set<Class<?>>> param = (Map<Class<?>, Set<Class<?>>>) paramGenerationMethod.invoke(null);

        // 调用测试方法
        Method method = AopHelper.class.getDeclaredMethod("createTargetMap", Map.class);
        method.setAccessible(true);
        Map<Class<?>, List<Proxy>> invokeResult = (Map<Class<?>, List<Proxy>>) method.invoke(null, param);

        // 判断结果集合不为空
        assertFalse(invokeResult.isEmpty());

        for (Map.Entry<Class<?>, List<Proxy>> entry : invokeResult.entrySet()) {
            // 判断key是不是带有Aspect注解的类
            Class<?> key = entry.getKey();
            assertTrue(key.isAnnotationPresent(Controller.class));

            List<Proxy> values = entry.getValue();
            // 判断value集合是否为空
            assertFalse(values.isEmpty());
            // values里的类，是不是带有controller注解的类
            values.forEach(instance -> {
                assertTrue(instance.getClass().isAnnotationPresent(Aspect.class));
            });
        }

    }

}