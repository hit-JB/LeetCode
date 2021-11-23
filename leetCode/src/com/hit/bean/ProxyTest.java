package com.hit.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author jb
 */
public class ProxyTest implements InvocationHandler {
    Object target;
    public static void main(String[] args) {
        HelloImpl raw = new HelloImpl();
        ProxyTest proxy = new ProxyTest(raw);
        SayHello instance = (SayHello)Proxy.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(), proxy);
        System.out.println(instance.answer());


    }
    public ProxyTest(Object target){
        this.target = target;
    }
    public ProxyTest(){

    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法开始之前,调用方法"+method.getName());
        Object result = method.invoke(this.target,args);
        System.out.println("方法调用完成之后");
        return result;
    }

    static class HelloImpl implements SayHello{

        @Override
        public String question() {
            System.out.println("this is a question?");
            return "this is a question result?";
        }

        @Override
        public String answer() {
            System.out.println("this is a answer result!");
            return "this is a answer!";
        }
    }
}
