package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author shijiapeng
 * @Date 2016/11/4 10:14
 * Created by shijiapeng on 2016/11/4.
 */
public class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        CLASS_SET = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage());
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 取得Service的集合
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }

        return classSet;
    }

    /**
     * 取得Controller集合
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }

        return classSet;
    }

    /**
     * 取得Controller和Service的集合
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }


    /**
     * 从所有的Class集合中，把所有是参数的子类或子接口的类取出来，然后形成一个集合返回去
     * @param superClass 父类或接口
     * @return 所有是参数的子类或子接口的类
     * @date 2016/11/29 上午10:24
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        // 声明返回值
        Set<Class<?>> result = new HashSet<>();
        // 循环判断哪个类是这个参数类的子类或子接口
        for (Class<?> clazz : CLASS_SET) {
            if (superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)) {
                result.add(clazz);
            }
        }
        return result;
    }


    /**
     * 通过Annotation Class，取得带有这个Annotation Class的所有类（业务类）
     * @param annotationClass 指定的注解
     * @return 带有指定注解的所有类
     * @date 2016/11/29 上午10:24
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        // 声明返回值
        Set<Class<?>> rtnClasses = new HashSet<>();
        // 循环所有Class，看哪个Class含有指定的annotation
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                rtnClasses.add(cls);
            }
        }

        return rtnClasses;
    }

    /**
     * 返回含有特定注解的类的集合，不论注解是类级别的还是方法级别的
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotationOfClassOrMethod
                                (Class<? extends Annotation> annotationClass) {
        // 声明返回值
        Set<Class<?>> rtnClasses = new HashSet<>();
        // 循环所有Class，看哪个Class含有指定的annotation
        for (Class<?> cls : CLASS_SET) {

            System.out.println("SimpleName:" + cls.getSimpleName());

            // 如果注解是类级别
            if (cls.isAnnotationPresent(annotationClass)) {
                rtnClasses.add(cls);
                continue;
            }

            // 方法查找主方法上带没带有注解
            Method[] methods = cls.getDeclaredMethods();
            if (ArrayUtil.isEmpty(methods))
                continue;

            for (Method method : methods) {
                if (method.isAnnotationPresent(Transaction.class)) {
                    rtnClasses.add(cls);
                    break;
                }
            }
        }

        return rtnClasses;
    }






}
