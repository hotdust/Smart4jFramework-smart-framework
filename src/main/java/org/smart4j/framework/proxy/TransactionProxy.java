package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 *
 *
 * Created by shijiapeng on 16/12/15.
 */
public class TransactionProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);


    // TODO: 16/12/15 解决属性注释里方法嵌套的事务问题
    /**
     * 这个标志位的作用是：保证一个线程内的事务处理只做一次。
     * 例如：有两个方法嵌套调用，两个方法都有Transaction注解的话，只执行一次。
     * 但这样好像有问题，如果两个方法都带有注解的话，里面的先执行，外面的事务就执行不了。
     *
     */
    private static ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {

        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean isExecuted = FLAG_HOLDER.get();
        Method targetMethod = proxyChain.getTargetMethod();
        // 是否 已经执行过事务 并且 目标方法带有Transaction注解
        if (!isExecuted && targetMethod.isAnnotationPresent(Transaction.class)) {

            try {
                // 开始事务
                DatabaseHelper.beginTransaction();
                System.out.println("---- beginTransaction ----");
                // 执行链调用
                result = proxyChain.doProxyChain();
                // 提交事务
                DatabaseHelper.commitTransaction();
                System.out.println("---- commitTransaction ----");
            } catch (Exception e) {
                // 发生异常就rollback
                LOGGER.error("Transaction Failure", e);
                DatabaseHelper.rollbackTransaction();
                System.out.println("---- rollbackTransaction ----");
                throw new RuntimeException(e);
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }

        return result;
    }
}
