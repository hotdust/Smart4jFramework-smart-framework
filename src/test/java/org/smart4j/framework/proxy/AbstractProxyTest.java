package org.smart4j.framework.proxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * @Author shijiapeng
 * @Date 2016/11/10 17:08
 * Created by shijiapeng on 2016/11/10.
 */
public class AbstractProxyTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: doProxy(ProxyChain proxyChain)
     *
     */
    @Test
    public void testDoProxy_inIntercept() throws Throwable {
        // 生成ProxyChain的mock对象
        ProxyChain chainMock = mock(ProxyChain.class);
        // 设置doProxyChain方法返回值为字符串
        String resultValue = "doProxy";
        when(chainMock.doProxyChain()).thenReturn(new String(resultValue));
        // 生成AbstractProxy对象的Spy
        AbstractProxy abstractProxy = spy(AbstractProxy.class);
        // 测试doProxy方法
        Object result = abstractProxy.doProxy(chainMock);
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
        AbstractProxy abstractProxy = spy(AbstractProxy.class);
        when(abstractProxy.intercept(null, null, null)).thenReturn(false);
        // 测试doProxy方法
        Object result = abstractProxy.doProxy(chainMock);
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
        AbstractProxy abstractProxy = spy(AbstractProxy.class);
        when(abstractProxy.intercept(null, null, null)).thenThrow(new RuntimeException());
        // 测试doProxy方法

        try {
            Object result = abstractProxy.doProxy(chainMock);
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
//TODO: Test goes here...
    }

    /**
     *
     * Method: after(Class<?> targetClass, Method targetMethod, Object[] methodParams)
     *
     */
    @Test
    public void testAfter() throws Exception {
//TODO: Test goes here...
    }

    /**
     *
     * Method: before(Class<?> targetClass, Method targetMethod, Object[] methodParams)
     *
     */
    @Test
    public void testBefore() throws Exception {
//TODO: Test goes here...
    }

    /**
     *
     * Method: begin()
     *
     */
    @Test
    public void testBegin() throws Exception {
//TODO: Test goes here...
    }

    /**
     *
     * Method: end()
     *
     */
    @Test
    public void testEnd() throws Exception {
//TODO: Test goes here...
    }


    /**
     *
     * Method: intercept(Class<?> targetClass, Method targetMethod, Object[] methodParams)
     *
     */
    @Test
    public void testIntercept() throws Exception {
//TODO: Test goes here...

    }
}
