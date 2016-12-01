package org.smart4j.framework.proxy;

import org.smart4j.framework.logic.ControllerAspect;
import org.smart4j.framework.logic.ControllerClass;
import org.smart4j.framework.bean.Param;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;


import static org.testng.AssertJUnit.*;

/**
 * @Author shijiapeng
 * @Date 2016/11/10 16:05
 * Created by shijiapeng on 2016/11/10.
 */
public class ControllerAspectTest {


    Class<ControllerClass> clazz;
    Method method;
    Param[] param = {new Param(new HashMap<String, Object>())};

    @BeforeMethod
    public void before() throws Exception {
        clazz = ControllerClass.class;
        method = clazz.getMethod("getMethoder", Param.class);

    }

    @AfterMethod
    public void after() throws Exception {
    }

    /**
     *
     * Method: before(Class<?> targetClass, Method targetMethod, Object[] methodParams)
     *
     */
    @Test
    public void testBefore() throws Exception {
        ControllerAspect aspect = new ControllerAspect();
        aspect.before(clazz, method, param);
    }

    /**
     *
     * Method: after(Class<?> targetClass, Method targetMethod, Object[] methodParams)
     *
     */
    @Test
    public void testAfter() throws Exception {
        ControllerAspect aspect = new ControllerAspect();
        aspect.after(clazz, method, param);
    }

    @Test
    public void testBeforeAndAfter() throws Exception {
        ControllerAspect aspect = new ControllerAspect();
        aspect.before(clazz, method, param);
        aspect.after(clazz, method, param);
    }
}
