package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 13:28
 * Created by shijiapeng on 2016/11/4.
 */
public class IoCHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IoCHelper.class);

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        // 如果Bean容器不为空，进行注入
        if (CollectionUtil.isNotEmpty(beanMap)) {
            // 对所有Bean进行循环注入
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
                    if (fieldInstance == null) {
                        String errorMessage = "Injecting instance does not exist";
                        LOGGER.error(errorMessage, entry);
                        throw new RuntimeException(errorMessage);
                    }

                    ReflectionUtil.setField(beanInstance, field, fieldInstance);
                }

            }
        }
    }
}
