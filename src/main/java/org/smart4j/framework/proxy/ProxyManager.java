package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author shijiapeng
 * @Date 2016/11/10 11:24
 * Created by shijiapeng on 2016/11/10.
 */
public class ProxyManager {

    public static Object createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(
                    Object targetObject,
                    Method method,
                    Object[] methodParams,
                    MethodProxy methodProxy) throws Throwable {

                return new ProxyChain(targetClass, targetObject, method, methodParams, methodProxy, proxyList).doProxyChain();
            }
        });
    }
}
