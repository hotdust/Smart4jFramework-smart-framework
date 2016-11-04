package org.smart4j.framework.helper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.helper.ClassHelper;

import java.util.Set;

/**
 * ClassHelper Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class ClassHelperTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: getClassSet()
     *
     */
    @Test
    public void testGetClassSet() throws Exception {
        Set<Class<?>> classSet = ClassHelper.getClassSet();
        Assert.assertNotNull(classSet);
    }

    /**
     *
     * Method: getServiceClassSet()
     *
     */
    @Test
    public void testGetServiceClassSet() throws Exception {
        Set<Class<?>> serviceClassSet = ClassHelper.getServiceClassSet();
        for (Class<?> cls : serviceClassSet) {
            Assert.assertTrue(cls.isAnnotationPresent(Service.class));
        }
    }

    /**
     *
     * Method: getControllerClassSet()
     *
     */
    @Test
    public void testGetControllerClassSet() throws Exception {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        for (Class<?> cls : controllerClassSet) {
            Assert.assertTrue(cls.isAnnotationPresent(Controller.class));
        }
    }

    /**
     *
     * Method: getBeanClassSet()
     *
     */
    @Test
    public void testGetBeanClassSet() throws Exception {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : classSet) {
            Assert.assertTrue(cls.isAnnotationPresent(Controller.class) || cls.isAnnotationPresent(Service.class));
        }
    }


} 
