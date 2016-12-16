package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;
import org.smart4j.framework.proxy.TransactionProxy;
import org.smart4j.framework.util.CollectionUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by shijiapeng on 16/11/28.
 */
public class AopHelper {

    // TODO: 16/12/5 把从总的Class_Set取得类的动作变成用name取得呢？

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            // 创建代理类和被代理类们的键值对Map
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            // 创建被代理类和它的代理类们的List的Map
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            // 创建被代理类的代理对象，并替换BeanHelper中的类和实例键值对
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxies = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxies);
                BeanHelper.setBean(targetClass, proxy);
            }

        } catch (Exception e) {
            LOGGER.error("AOP initialization error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建代理（Proxy）和目标对象们的键值对
     * @Author shijiapeng
     * @date 2016/11/29 上午9:51
     * @return
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        // 声明返回对象
        Map<Class<?>, Set<Class<?>>> rtnMap = new HashMap<Class<?>, Set<Class<?>>>();

        // 添加所有的切面类
        addAspectProxy(rtnMap);
        // 添加所有事务类(应该放到链的最后面，来只保证目标方法的事务完整性)
        addTransactionProxy(rtnMap);

        return rtnMap;
    }

    /**
     * 添加所有切面代理类和目标对象们的Map
     * @param proxyMap 收集用Map。把结果添加到这个Map里
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        // 取得所有定义的切面类
        Set<Class<?>> proxyClasses = ClassHelper.getClassSetBySuper(AspectProxy.class);
        // 根据代理类的注解里的value值（也就是Aspect(contorller.class)中的controller.class），
        // 取得被代理的集合
        for (Class<?> proxyClass : proxyClasses) {
            // 判断注解是不是Aspect
            if (!proxyClass.isAnnotationPresent(Aspect.class))
                continue;

            // 取得带有注解value值（值是一个注解）的业务类集合
            Aspect annotation = proxyClass.getAnnotation(Aspect.class);
            Set<Class<?>> targetClasses = createTargetClassSet(annotation);
            proxyMap.put(proxyClass, targetClasses);
        }
    }


    /**
     * 添加所有事务代理类和目标对象们的Map
     * @param proxyMap 收集用Map。把结果添加到这个Map里
     */
    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> transactionTargetSet =
                ClassHelper.getClassSetByAnnotationOfClassOrMethod(Transaction.class);
        if (!CollectionUtil.isEmpty(transactionTargetSet))
            proxyMap.put(TransactionProxy.class, transactionTargetSet);
    }




    /**
     * 取得类的集合，这些类包含"指定注解里面的value值所代表的注解"
     * @param aspect
     * @return
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        Set<Class<?>> rtnClasses = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        // idea提示 annotation != null 是无用代码
        if (!annotation.equals(Aspect.class)) {
            rtnClasses.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }

        return rtnClasses;
    }


    /**
     * 创建目标对象和代理们的键值对，和createProxyMap结果正相反，createProxyMap的目的就是为了创建createTargetMap。
     *
     * @param proxyMap 取得类的集合，这些类包含"指定注解里面的value值所代表的注解"
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @Author shijiapeng
     * @date 2016/11/29 上午9:51
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(
            Map<Class<?>, Set<Class<?>>> proxyMap)
            throws IllegalAccessException, InstantiationException {
        Map<Class<?>, List<Proxy>> rtnClasses = new HashMap<>();

        // 循环所有被代理的类，组成一个被代理类和它的代理们的键值对
        for (Map.Entry<Class<?>, Set<Class<?>>> classSetEntry : proxyMap.entrySet()) {
            // 取得代理类
            Class<?> proxyClass = classSetEntry.getKey();
            // 循环代理类的子类
            for (Class<?> targetClass : classSetEntry.getValue()) {
                Proxy proxy = (Proxy)proxyClass.newInstance();

                if (rtnClasses.containsKey(targetClass)) {
                    rtnClasses.get(targetClass).add(proxy);
                } else {
                    ArrayList<Proxy> proxies = new ArrayList<>();
                    proxies.add(proxy);
                    rtnClasses.put(targetClass, proxies);
                }
            }
        }

        return rtnClasses;
    }
}
