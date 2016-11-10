package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;

import java.lang.reflect.Method;

/**
 * @Author shijiapeng
 * @Date 2016/11/10 15:49
 * Created by shijiapeng on 2016/11/10.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AbstractProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long beginTime;

    @Override
    public void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
        LOGGER.debug("------------ begin -------------");
        LOGGER.debug(String.format("class: %s", targetClass.getName()));
        LOGGER.debug(String.format("method: %s", targetMethod.getName()));
        beginTime = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
        LOGGER.debug(String.format("eclipsed time: %dms", System.currentTimeMillis() - beginTime));
        LOGGER.debug("------------ end -------------");
    }
}
