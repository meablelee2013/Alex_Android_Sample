package com.oriente.aptsample;

import org.junit.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    static class ObjectInstance {
        public static volatile ObjectInstance INSTANCE = new ObjectInstance();
        private List<Object> objects = new ArrayList<>();

        public void addObject(Object object) {
            objects.add(object);
        }
    }

    /**
     * 通过单例持有对象
     *---------------------------------没有内存漏泄-----------------------------------------
     *
     * 当ObjectInstance.addObject是个空方法时结果如下：
     *
     * 软引用soft.get():   java.lang.Object@7526515b
     * 软引用softQueue.poll() :   null
     * object =null 并GC执行...
     * 软引用soft.get():   java.lang.Object@7526515b
     * 软引用softQueue.poll() :   null
     * ===============================
     *
     * 弱引用weak.get():   java.lang.Object@6e9175d8
     * 弱引用 weakQueue.poll():   null
     * object =null 并GC执行...
     * 弱引用weak.get():   null
     * 弱引用 weakQueue.poll():   java.lang.ref.WeakReference@7d0b7e3c
     * ===============================
     * 结论：软引用在漏泄之前和之后没有任务区别，因为软引用只有在内存不足时才会回收老的对象
     *      而弱引用
     *              泄漏之前 weak.get()为null,但其关联的引用队列不为null
     *              漏泄之后 weak.get()不为null,但其关联的引用队列为null
     *           所以我们可以通过检测弱引用关联的队列，如果强引用对象object=null之后，弱引用
     *           关联的引用对象.poll之后为null表示没有漏泄，否则就漏泄了
     *
     *
     *
     * ---------------------------------内存泄漏之后-----------------------------------------
     *
     * 当ObjectInstance.addObject是个不是空方法时结果如下： 不是空方法意味着object被一个单例ObjectInstance
     * 持有，当object被置为null时也依然被单例持有，造成内存泄漏
     *
     * 软引用soft.get():   java.lang.Object@7526515b
     * 软引用softQueue.poll() :   null
     * object =null 并GC执行...
     * 软引用soft.get():   java.lang.Object@7526515b
     * 软引用softQueue.poll() :   null
     * ===============================
     *
     * 弱引用weak.get():   java.lang.Object@6e9175d8
     * 弱引用 weakQueue.poll():   null
     * object =null 并GC执行...
     * 弱引用weak.get():   java.lang.Object@6e9175d8
     * 弱引用 weakQueue.poll():   null
     * ===============================
     * @throws InterruptedException
     */

    @Test
    public void addition_isCorrect() throws InterruptedException {
        Object object = new Object();
        ObjectInstance.INSTANCE.addObject(object);
        ReferenceQueue<Object> softQueue = new ReferenceQueue<>();
        SoftReference<Object> soft = new SoftReference<>(object, softQueue);
        print("软引用soft.get():   " + soft.get());
        print("软引用softQueue.poll() :   " + softQueue.poll());

        object = null;
        System.gc();
        Thread.sleep(1000);
        print("object =nll 并GC执行...");
        print("软引用soft.get():   " + soft.get());
        print("软引用softQueue.poll() :   " + softQueue.poll());
        print("===============================");
        print("");

        object = new Object();
        ObjectInstance.INSTANCE.addObject(object);
        ReferenceQueue<Object> weakQueue = new ReferenceQueue<>();
        WeakReference<Object> weak = new WeakReference<>(object, weakQueue);
        print("弱引用weak.get():   " + weak.get());
        print("弱引用 weakQueue.poll():   " + weakQueue.poll());
        object = null;
        System.gc();
        Thread.sleep(1000);
        print("object =nll 并GC执行...");
        print("弱引用weak.get():   " + weak.get());
        print("弱引用 weakQueue.poll():   " + weakQueue.poll());
        print("===============================");


    }

    private void print(String str) {
        System.out.println(str);
    }
}