package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.logic.ControllerAspect;
import org.smart4j.framework.logic.ControllerClass;
import org.smart4j.framework.proxy.AspectProxy;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.AssertJUnit.*;

/**
 * ClassHelper Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class ClassHelperTest {



    @Test
    public void getClassSetBySuper() throws Exception {
        Set<Class<?>> classSetBySuper = ClassHelper.getClassSetBySuper(AspectProxy.class);
        assertTrue(classSetBySuper.size() > 0);
        classSetBySuper.forEach(
                cls -> assertTrue(cls.equals(ControllerAspect.class))
        );
    }

    @Test
    public void getClassSetByAnnotation() throws Exception {
        Set<Class<?>> classSetBySuper = ClassHelper.getClassSetByAnnotation(Aspect.class);
        assertTrue(classSetBySuper.size() > 0);
        classSetBySuper.forEach(
                cls -> assertTrue(cls.equals(ControllerAspect.class))
        );
    }

    @BeforeMethod
    public void before() throws Exception {
    }

    @AfterMethod
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
        assertNotNull(classSet);
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
            assertTrue(cls.isAnnotationPresent(Service.class));
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
            assertTrue(cls.isAnnotationPresent(Controller.class));
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
            assertTrue(cls.isAnnotationPresent(Controller.class) || cls.isAnnotationPresent(Service.class));
        }
    }


} 
