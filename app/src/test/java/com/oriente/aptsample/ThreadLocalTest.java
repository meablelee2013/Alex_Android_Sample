package com.oriente.aptsample;

import org.junit.Test;

public class ThreadLocalTest {

    /**
     * return
     *      thread1  李四
     *      thread2  王五
     *      null
     * 知识点1：
     * ThreadLocal并不是在new的时候初始化，而是在set或者get的时候初始化
     * 在Thread1 线程中，两次y调用set最后取出的值是最后一次set的值，是因为thread1当前线程的ThreadLocalMap 的key就是tl,
     *        且是在set("张三")时初始化的tl，当完成了初始化之后，当前线程（Thread1）里的tl就不会变化了，也就是key是固定的
     *         后面再set值只会不停的覆盖，同理在thread2中set之后thread2中的tl也完成了初始化，而在46行也就是在主线程中的tl
     *         并没有调用set get方法，并没完成初始化，所以get的时候值为null
     * 知识点2：
     * ThreadLocal里的ThreadLocalMap的key是一个弱引用，也就是说当我们不需要threadLocal时可以调用tl=null，也就是
     *         ThreadLocalMap里的key的弱引用里的对象tl为null，那key也就能gc掉了，但value依然不会被gc，此时最优雅的方法就是
     *         调用tl.remove()
     *
     *
     *
     *
     */
    @Test
    public void threadLocalTest(){
        ThreadLocal<String> tl = new ThreadLocal<>();
        //thread1
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            tl.set("张三");
            tl.set("李四");
            System.out.println("thread1  "+tl.get());//thread1  李四
        }).start();
        //thread2
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            tl.set("thread2  "+"王五");
            System.out.println(tl.get());//thread2  王五
        }).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(tl.get());//null

    }
}
