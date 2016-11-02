package org.smart4j.framework.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * ClassUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>ʮһ�� 2, 2016</pre>
 */
public class ClassUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getClassLoader()
     */
    @Test
    public void testGetClassLoader() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: loadClass(String className, boolean isInitialized)
     */
    @Test
    public void testLoadClass() throws Exception {
        Class<?> cls = ClassUtil.loadClass("org.smart4j.framework.helper.ConfigHelper", false);
        System.out.println(cls.getName());
    }

    /**
     * Method: getClassSet(String packageName)
     */
    @Test
    public void testGetClassSet() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: doAddClass(Set<Class<?>> classSet, String className)
     */
    @Test
    public void testDoAddClass() throws Exception {
        Set<Class<?>> rtnSet = new HashSet<Class<?>>();
        Method method = ClassUtil.class.getDeclaredMethod("doAddClass", Set.class, String.class);
        method.setAccessible(true);
        method.invoke(null, rtnSet, "org.smart4j.framework.helper.ConfigHelper");
        Assert.assertEquals(rtnSet.size(), 1);
    }

    /**
     * Method: addClass(Set<Class<?>> classSet, String packagePath, String packageName)
     */
    @Test
    public void testAddClass() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ClassUtil.getClass().getMethod("addClass", Set<Class<?>>.class, String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
