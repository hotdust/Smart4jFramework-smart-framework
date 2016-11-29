package org.smart4j.framework.proxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.framework.bean.Param;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @Author shijiapeng
 * @Date 2016/11/10 16:05
 * Created by shijiapeng on 2016/11/10.
 */
public class ControllerAspectTest {


    Class<CustomerController> clazz;
    Method method;
    Param[] param = {new Param(new HashMap<String, Object>())};

    @Before
    public void before() throws Exception {
        clazz = CustomerController.class;
        method = clazz.getMethod("index", Param.class);

    }

    @After
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
