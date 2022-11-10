package com.oriente.aptsample;

import android.os.SystemClock;
import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ThreadUnitTest {

    @Test
    public void testJoin() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //执行第一步
                System.out.println("第一步执行完成");
                //执行第二步
                System.out.println("第二步执行完成");
                //执行第三步
                System.out.println("第三步执行完成");
            }
        });
        t1.start();
        t1.join();//等待t1执行完才会执行t2
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行第二个线程任务");
            }
        });
        t2.start();
        t2.join();

    }

    @Test
    public void testNotify() throws InterruptedException {
        Object lock = new Object();
        ArrayList<String> data = new ArrayList<>();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    //执行第一步
                    data.add("1");
                    System.out.println("第一步执行完成");
                    //执行第二步
                    data.add("1");
                    System.out.println("第二步执行完成");
                    lock.notify();
                    //执行第三步
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    data.add("3");
                    System.out.println("第三步执行完成");
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();//释放lock锁,相当于没有锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    data.add("4");
                    System.out.println("执行第二个线程任务");
                }

            }
        });
        t2.start();
        t1.start();

        t1.join();
        t2.join();

    }

    @Test
    public void testCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务3等待执行");
                try {
                    countDownLatch.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("任务3执行完成");
            }
        }).start();
//        Thread.sleep(1000);
        System.out.println("任务1执行完成");
        countDownLatch.countDown();
//        Thread.sleep(1000);
        System.out.println("任务2执行完成");
        countDownLatch.countDown();
    }

}
