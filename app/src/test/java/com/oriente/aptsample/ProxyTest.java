package com.oriente.aptsample;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    @Test
    public void dynamicTest() {
        Person person = new Monkey();
        Person proxyPerson = (Person) Proxy.newProxyInstance(person.getClass().getClassLoader(), person.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName() + "这个方法进来 了");
                if ("setAge".equals(method.getName())) {
                    System.out.println("请把我变年轻一些吧");
                    args[0] = 58;
                }
                method.invoke(person, args);
                return null;
            }
        });
        person.getAge();

        person.setAge(100);

        person.getAge();
        System.out.println("替换了系统的方法------");

        proxyPerson.setAge(18);

        proxyPerson.getAge();

    }

}

class Monkey implements Person {
    String name = "alex";
    int age = 18;

    public void setAge(int age) {
        System.out.println("我是" + name + "我想回到" + age + "岁");
        this.age = age;
    }

    public void getAge() {
        System.out.println("我是" + name + "，今年" + age + "岁了");
    }
}

interface Person {
    void getAge();

    void setAge(int age);
}

