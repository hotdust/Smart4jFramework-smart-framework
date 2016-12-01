package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Author shijiapeng
 * @Date 2016/11/10 14:15
 * Created by shijiapeng on 2016/11/10.
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();

        // TODO: 2016/11/10 begin和end是做什么用的？
        begin();

        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("Proxy error", e);
            error(targetClass, targetMethod, methodParams, e);
            throw e;
        }finally {
            end();
        }

        return result;
    }

    /**
     * 一个钩子方法，用来判断是否要执行增强
     * @param targetClass
     * @param targetMethod
     * @param methodParams
     * @return
     */
    public boolean intercept(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
        return true;
    }

    public void error(Class<?> targetClass, Method targetMethod, Object[] methodParams, Exception e) {
    }

    public void after(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
    }

    public void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
    }
    /**
     * 前置处理
     */
    public void begin() {
        // nothing
    }

    /**
     * 后置处理
     */
    public void end() {
//        nothing
    }
}
