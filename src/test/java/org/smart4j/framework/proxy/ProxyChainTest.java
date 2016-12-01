package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

import java.util.List;


import static org.mockito.Mockito.*;

/**
 * Created by shijiapeng on 16/11/28.
 */
public class ProxyChainTest {


    @Test
    public void doProxyChain_sizeGreaterThanProxyIndex() throws Throwable {
        String result = "greaterThan1";

        MethodProxy methodProxy = mock(MethodProxy.class);
        List<Proxy> proxyList = mock(List.class);
        Proxy proxy = mock(Proxy.class);

        // 为了测试proxyIndex < proxyList.size()，把size设置1，proxyIndex默认为0
        when(proxyList.size()).thenReturn(1);
        // 设置返回结果
        when(proxyList.get(anyInt())).thenReturn(proxy);
        when(proxyList.get(anyInt()).doProxy(anyObject())).thenReturn(result);

        // 执行
        Object executeResult = new ProxyChain(null, null, null, null, methodProxy, proxyList).doProxyChain();

        assertTrue(executeResult.equals(result));
    }


    @Test
    public void doProxyChain_sizeNotGreaterThanProxyIndex() throws Throwable {
        String result = "notGreaterThan1";

        MethodProxy methodProxy = mock(MethodProxy.class);
        List<Proxy> proxyList = mock(List.class);

        // 为了测试proxyIndex >= proxyList.size()，把size设置9，proxyIndex默认为0
        when(proxyList.size()).thenReturn(0);
        // 设置返回结果
        when(methodProxy.invokeSuper(anyObject(), anyObject())).thenReturn(result);

        // 执行
        Object executeResult = new ProxyChain(null, null, null, null, methodProxy, proxyList).doProxyChain();

        assertTrue(executeResult.equals(result));
    }

    @Test
    public void getTargetClass() throws Exception {

    }

    @Test
    public void getTargetMethod() throws Exception {

    }

    @Test
    public void getMethodParams() throws Exception {

    }
/*

    public class ProxyChain {

        private final Class<?> targetClass;
        private final Object targetObject;
        private final Method targetMethod;
        private final Object[] methodParams;
        private final MethodProxy methodProxy;
        private final List<Proxy> proxyList;

        private int proxyIndex = 0;

        public ProxyChain(Class<?> targetClass,
                          Object targetObject,
                          Method targetMethod,
                          Object[] methodParams,
                          MethodProxy methodProxy,
                          List<Proxy> proxyList) {
            this.targetClass = targetClass;
            this.targetObject = targetObject;
            this.targetMethod = targetMethod;
            this.methodParams = methodParams;
            this.methodProxy = methodProxy;
            this.proxyList = proxyList;
        }

        public Object doProxyChain() throws Throwable {
            Object result;
            if (proxyIndex < proxyList.size()) {
                result = proxyList.get(proxyIndex++).doProxy(this);
            } else {
                result = methodProxy.invokeSuper(targetObject, methodParams);
            }
            return result;
        }

        public Class<?> getTargetClass() {
            return targetClass;
        }

        public Method getTargetMethod() {
            return targetMethod;
        }

        public Object[] getMethodParams() {
            return methodParams;
        }
    }
*/


}