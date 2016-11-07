package org.smart4j.framework.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
        Assert.assertTrue(ClassUtil.getClassLoader() instanceof ClassLoader);
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

//        String packagePath = "D:/IdeaProjects/Smart4jFramework/smart-framework/target/classes/org/smart4j/framework";
//        String packageName = "org.smart4j.framework";

        String packagePath = "D:/IdeaProjects/Smart4jFramework/smart-framework/target/classes/org/smart4j/framework";
        String packageName = "org.smart4j.framework";
        Set<Class<?>> rtnSet = new HashSet<Class<?>>();

        Method method = ClassUtil.class.getDeclaredMethod("addClass", Set.class, String.class, String.class);
        method.setAccessible(true);
        method.invoke(null, rtnSet, packagePath, packageName);

        Assert.assertTrue(rtnSet.size() > 0);
    }


    /**
     * Method: getClassSet(String packageName)
     */
    @Test
    public void testGetClassSet() throws Exception {
//        URL url = Thread.currentThread().getContextClassLoader().getResource("org/smart4j/framework/util/test.jar");
//        System.out.println(url);
//        JarURLConnection jarURLConnection = ((JarURLConnection) url.openConnection());
//        // 如果jarConnection为空，则跳过
//        if (jarURLConnection == null) {
//        }
//
//        JarFile jarFile = jarURLConnection.getJarFile();
//        if (jarFile == null) {
//        }
//
//        Enumeration<JarEntry> jarEntries = jarFile.entries();
//        while (jarEntries.hasMoreElements()) {
//            JarEntry jarEntry = jarEntries.nextElement();
//            String jarEntryName = jarEntry.getName();
//            // 如果entry不是class文件，跳过
//            if (".class".equals(jarEntryName)) {
//                continue;
//            }
//
//            String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
//        }



//        URL urlTest = new URL("file:D:/IdeaProjects/Smart4jFramework/smart-framework/target/test-classes/org/smart4j/framework/util/test.jar");
//        URL urlTest = new URL("jar:file:D:/IdeaProjects/Smart4jFramework/smart-framework/target/test-classes/org/smart4j/framework/util/test.jar!/");
//
//        URL url = Thread.currentThread().getContextClassLoader().getResource("org/smart4j/framework/util/test.jar");
//        System.out.println(url);
//
//        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("org/smart4j/framework/util");
//        while (resources.hasMoreElements()) {
//            URL url_1 = resources.nextElement();
//            System.out.println(url_1);
//        }


        // 为了指classpath根目录下的Jar文件的测试
//        ClassUtil.getClassSet("org/smart4j/framework/util/test.jar");


        // 正常的测试用例
        String packageName = "org.smart4j.framework";
        Set<Class<?>> classSet = ClassUtil.getClassSet(packageName);
        System.out.println(classSet.size());

    }

} 
