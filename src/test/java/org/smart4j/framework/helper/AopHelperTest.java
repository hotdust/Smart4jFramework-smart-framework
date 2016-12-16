package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.logic.ControllerAspect;
import org.smart4j.framework.logic.ControllerClass;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.TransactionProxy;
import org.smart4j.framework.util.CollectionUtil;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.TrustAnchor;
import java.util.HashMap;
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

        boolean aspectProxyExists = false;
        boolean transactionProxyExists = false;
        for (Map.Entry<Class<?>, Set<Class<?>>> entry : invokeResult.entrySet()) {
            Class<?> key = entry.getKey();

            if (key.isAnnotationPresent(Aspect.class)) {
                aspectProxyExists = true;
            } else if (key == TransactionProxy.class) {
                transactionProxyExists = true;
            } else {
                assertFalse(true);
            }
        }

        assertTrue(aspectProxyExists && transactionProxyExists);

    }

    @Test
    public void testAddAspectProxy() throws Exception {
        // 调用方法时传的参数
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        // 调用测试方法
        Method method = AopHelper.class.getDeclaredMethod("addAspectProxy", Map.class);
        method.setAccessible(true);
        method.invoke(null, proxyMap);


        // 判断结果集合不为空
        assertFalse(proxyMap.isEmpty());

        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
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
            });
        }

    }

    @Test
    public void testAddTransactionProxy() throws Exception {
        // 调用方法时传的参数
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        // 调用测试方法
        Method method = AopHelper.class.getDeclaredMethod("addTransactionProxy", Map.class);
        method.setAccessible(true);
        method.invoke(null, proxyMap);

        // 判断结果集合不为空
        assertFalse(proxyMap.isEmpty());

        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            // 判断key是不是带有Aspect注解的类
            Class<?> key = entry.getKey();

            // 目标类中，带有事务注解
            Set<Class<?>> values = entry.getValue();
            values.forEach(cls -> {
                if (cls.isAnnotationPresent(Service.class)) {
                    assertTrue(true);
                } else {
                    assertTrue(false);
                }
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