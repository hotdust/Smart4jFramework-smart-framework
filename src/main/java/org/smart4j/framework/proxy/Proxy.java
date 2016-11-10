package org.smart4j.framework.proxy;

/**
 * @Author shijiapeng
 * @Date 2016/11/10 9:35
 * Created by shijiapeng on 2016/11/10.
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
