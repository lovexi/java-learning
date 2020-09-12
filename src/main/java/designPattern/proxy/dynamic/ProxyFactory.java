package designPattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    private Object target;

    public ProxyFactory(Object obj) {
        this.target = obj;
    }

    public Object getProxyInstance() {
        /*
        1. classloader loader: current class loader for the target object
        2. class<?>[] interfaces: current target interface types, using generic
        3/ InvocationHandler h: the event handler, will invoke handler method
         */
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("JDK proxy starts.");
                return method.invoke(target, args);
            }
        });
    }
}
