package org.smart4j.framework.helper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.ClassUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * ClassHelper Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class IoCHelperTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * 测试是否把Bean都注入了
     *
     */
    @Test
    public void testInjection() throws Exception {
        ClassUtil.loadClass(IoCHelper.class.getName());
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
            Class<?> beanKey = entry.getKey();
            Object beanInstance = entry.getValue();

            Field[] fields = beanKey.getDeclaredFields();
            // 如果当前类没有注入，继续下一个类
            if (ArrayUtil.isEmpty(fields)) {
                continue;
            }
            for (Field field : fields) {
                // 如果没有@Inject注解，继续下一个
                if (!field.isAnnotationPresent(Inject.class)) {
                    continue;
                }


                Class<?> fieldType = field.getType();
                Object fieldInstance = BeanHelper.getBean(fieldType);

                field.setAccessible(true);
                boolean compareResult = field.get(beanInstance).getClass().getName().equals(
                        fieldInstance.getClass().getName());
                System.out.println(compareResult);
                Assert.assertTrue(compareResult);
            }

        }
    }


} 
