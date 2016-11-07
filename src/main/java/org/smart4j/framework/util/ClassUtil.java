package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Author shijiapeng
 * @Date 2016/11/2 16:53
 * Created by shijiapeng on 2016/11/2.
 */
public class ClassUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);
    public static final String PROTOCOL_FILE = "file";
    public static final String PROTOCOL_JAR = "jar";


//    private static int testStaticMethod(Set<Class<?>> set) {
//    private static int testStaticMethod(int i) {
//        return 1;
//    }

    /**
     * 取得ClassLoader
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class error", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    // TODO: 2016/11/4 读取Jar里面的类的内容的代码还没有测试
    public static Set<Class<?>> getClassSet(String packageName) {
        // 声明返回值
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        // 取得包下所有文件
        try {
            Enumeration<URL> resources = getClassLoader().getResources(packageName.replace(".","/"));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                // 如果url为空，继续下一个
                if (url == null) {
                    continue;
                }

                // 取得协议类型
                String protocol = url.getProtocol();
                if (PROTOCOL_FILE.equals(protocol)) {
                    // 把%20换成空格
                    String packagePath = url.getPath().replace("%20", " ");
                    addClass(classSet, packagePath, packageName);
                } else if (PROTOCOL_JAR.equals(protocol)) {
                    JarURLConnection jarURLConnection = ((JarURLConnection) url.openConnection());
                    // 如果jarConnection为空，则跳过
                    if (jarURLConnection == null) {
                        continue;
                    }

                    JarFile jarFile = jarURLConnection.getJarFile();
                    if (jarFile == null) {
                        continue;
                    }

                    Enumeration<JarEntry> jarEntries = jarFile.entries();
                    while (jarEntries.hasMoreElements()) {
                        JarEntry jarEntry = jarEntries.nextElement();
                        String jarEntryName = jarEntry.getName();
                        // 如果entry不是class文件，跳过
                        if (".class".equals(jarEntryName)) {
                            continue;
                        }

                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
                        doAddClass(classSet, className);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("get class set error", e);
            throw new RuntimeException(e);
        }
        // 循环读文件
        // 如果是file的话，加载
        // 如果是Jar文件的话，解析Jar
        return classSet;

    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        // 取得路径下的所有.class文件和目录
//        File[] files = new File(packagePath).listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File file) {
//                boolean acceptCondition = (file.isFile() && file.getName().endsWith(".class"))
//                        || file.isDirectory();
//                return acceptCondition;
//            }
//        });
        File[] files = new File(packagePath).listFiles((File file) ->
                (file.isFile() && file.getName().endsWith(".class"))
                || file.isDirectory());

        // TODO: 2016/11/2 看看如果files是null的话，是不是报null exception
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }

                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    public static void main(String[] args) {
        ClassUtil.getClassSet("javax.crypto.spec");
    }
}
