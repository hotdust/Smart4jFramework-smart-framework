package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 这个类的作用是：控制增强方法和被代理方法的调用时机。
 * 即，"先调用所有切面的增强方法，再调用被代理方法"的顺序进行调用。
 * 关于增强方法里的"前置增强"和"后置增强"的调用顺序，是在AspectProxy里控制的。
 *
 * AspectProxy和ProxyChain的关系可能有点乱，再简单的说一下：
 *  - AspectProxy控制"前置增强"、"被代理方法"和"后置增强"的调用顺序
 *  - ProxyChain控制 所有的"前置增强"执行完后，再执行被代理方法，再执行"后置增强"
 *    （AspectProxy和ProxyChain之间的调用，是一个很巧妙的设计）
 *  （如果不这么设计，用最笨的方法应该怎么设计呢？）
 *
 *  如果用最笨的方法设计的话：
 *  1，在加入单个的前置增强或后置增强时，必须在"被调用方法前/后"进行类型判断（看是前置还是后置）；
 *     或者增加两个List（前置List和后置List），在被调用方法前后调用。
 *     如果用这种设计方式的话，就把前置增强处理逻辑（先调用增强，再调用被调用被代理方法）和
 *     后置增强逻辑（先调用被代理方法，再调用增强）放到了前置增强和后置增强的基类中了，不用放到
 *     生成代理的基类中了（例如：AspectProxy）。如果再增加其它增强（例如环绕增强），就不用再去修改
 *     生成代理的基类了（影响比较小），只要在新增加的增强类中进行进行调用顺序的安排就可以了，
 *     也算是一种设计模式。（例如，要增加环绕增强的话，在环绕增强的处理中，先调用环绕前前置，
 *     再调用doProxyChain，再调用环绕后置增强，就像这里的AspectProxy的样子）
 *
 *
 *
 * @Author shijiapeng
 * @Date 2016/11/10 11:22
 * Created by shijiapeng on 2016/11/10.
 */
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
