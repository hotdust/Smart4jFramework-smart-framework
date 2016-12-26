package org.smart4j.framework.proxy;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.testng.AssertJUnit.*;

/**
 * @Author shijiapeng
 * @Date 2016/11/10 17:08
 * Created by shijiapeng on 2016/11/10.
 */
public class AspectProxyTest {

    @BeforeMethod
    public void before() throws Exception {
    }

    @AfterMethod
    public void after() throws Exception {
    }

    /**
     *
     * Method: doProxy(ProxyChain proxyChain)
     *
     */
//    @Test
//    public void testDoProxy_inIntercept() throws Throwable {
//        // 生成ProxyChain的mock对象
//        ProxyChain chainMock = mock(ProxyChain.class);
//        // 设置doProxyChain方法返回值为字符串
//        String resultValue = "doProxy";
//        when(chainMock.doProxyChain()).thenReturn(new String(resultValue));
//        // 生成AbstractProxy对象的Spy
//        AspectProxy abstractProxy = spy(AspectProxy.class);
//        // 测试doProxy方法
//        Object result = abstractProxy.doProxy(chainMock);
//        assertTrue(result.equals(resultValue));
//    }
    @Test
    public void testDoProxy_inIntercept() throws Throwable {
        // 生成ProxyChain的mock对象
        ProxyChain chainMock = mock(ProxyChain.class);
        // 设置doProxyChain方法返回值为字符串
        String resultValue = "doProxy";
        when(chainMock.doProxyChain()).thenReturn(new String(resultValue));
        // 生成AbstractProxy对象的Spy
        AspectProxy aspectProxy = mock(AspectProxy.class);
        when(aspectProxy.doProxy(chainMock)).thenCallRealMethod();
        // 测试doProxy方法
        Object result = aspectProxy.doProxy(chainMock);
        assertTrue(result.equals(resultValue));
    }

    @Test
    public void testDoProxy_outIntercept() throws Throwable {
        // 生成ProxyChain的mock对象
        ProxyChain chainMock = mock(ProxyChain.class);
        // 设置doProxyChain方法返回值为字符串
        String resultValue = "doProxy";
        when(chainMock.doProxyChain()).thenReturn(new String(resultValue));
        // 生成AbstractProxy对象的Spy
        AspectProxy aspectProxy = mock(AspectProxy.class);
        when(aspectProxy.doProxy(chainMock)).thenCallRealMethod();

        when(aspectProxy.intercept(null, null, null)).thenReturn(false);
        // 测试doProxy方法
        Object result = aspectProxy.doProxy(chainMock);
        assertTrue(result.equals(resultValue));
    }

    @Test
    public void testDoProxy_Exception() throws Throwable {
        // 生成ProxyChain的mock对象
        ProxyChain chainMock = mock(ProxyChain.class);
        // 设置doProxyChain方法返回值为字符串
        String resultValue = "doProxy";
        when(chainMock.doProxyChain()).thenReturn(new String(resultValue));
        // 生成AbstractProxy对象的Spy
        AspectProxy aspectProxy = mock(AspectProxy.class);
        when(aspectProxy.doProxy(chainMock)).thenCallRealMethod();
        when(aspectProxy.intercept(null, null, null)).thenThrow(new RuntimeException());
        // 测试doProxy方法

        try {
            Object result = aspectProxy.doProxy(chainMock);
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (Exception e){}
    }

    /**
     *
     * Method: error(Class<?> targetClass, Method targetMethod, Object[] methodParams, Exception e)
     *
     */
    @Test
    public void testError() throws Exception {
    }

    /**
     *
     * Method: after(Class<?> targetClass, Method targetMethod, Object[] methodParams)
     *
     */
    @Test
    public void testAfter() throws Exception {
    }

    /**
     *
     * Method: before(Class<?> targetClass, Method targetMethod, Object[] methodParams)
     *
     */
    @Test
    public void testBefore() throws Exception {
    }

    /**
     *
     * Method: begin()
     *
     */
    @Test
    public void testBegin() throws Exception {
    }

    /**
     *
     * Method: end()
     *
     */
    @Test
    public void testEnd() throws Exception {
    }


    /**
     *
     * Method: intercept(Class<?> targetClass, Method targetMethod, Object[] methodParams)
     *
     */
    @Test
    public void testIntercept() throws Exception {

    }
}
