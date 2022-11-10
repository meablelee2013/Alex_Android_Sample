package com.oriente.aptsample;

import android.os.SystemClock;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class CountDownLatchTest {

    @Test
    public void testCountDownLatch(){
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务3等待执行");
                try {
                    countDownLatch.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务3执行完成");
            }
        }).start();
        SystemClock.sleep(1000);
        System.out.println("任务1执行完成");
        countDownLatch.countDown();
        SystemClock.sleep(1000);
        System.out.println("任务2执行完成");
        countDownLatch.countDown();
    }
}
