package com.oriente.aptsample;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

/**
 * 演示软引用
 * -Xmx20m -XX:+PrintGCDetails -verbose:gc
 */
public class ExampleUnitTest {
    private static final int _4MB = 4 * 1024 * 1024;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSoftReference() {
//        List<SoftReference<byte[]>> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            SoftReference<byte[]> ref = new SoftReference<>(new byte[_4MB]);
//            System.out.println(ref.get());
//            list.add(ref);
//            System.out.println(list.size());
//        }
//
//        System.out.println("循环结束: " + list.size());
//        for (SoftReference<byte[]> ref : list) {
//            System.out.println(ref.get());
//        }
    }

    //查看对象占用字节数

    /**
     *  java.lang.Object object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x000000000000000d (biasable; age: 1)
     *   8   4        (object header: class)    0x00001000
     *  12   4        (object alignment gap)
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     *
     *  com.oriente.aptsample.MyObject object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x000000000000000d (biasable; age: 1)
     *   8   4        (object header: class)    0x0016a7b8
     *  12   4    int MyObject.a                1                                  //MyObject 里有一个int a = 1  int 占用4个字节
     * Instance size: 16 bytes
     * Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
     *
     *
     *  com.oriente.aptsample.MyObject object internals:
     * OFF  SZ     TYPE DESCRIPTION               VALUE
     *   0   8          (object header: mark)     0x0000000000000005 (biasable; age: 0)
     *   8   4          (object header: class)    0x0016a7b8
     *  12   4      int MyObject.a                1
     *  16   8   double MyObject.b                10.0
     * Instance size: 24 bytes
     * Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
     *
     *
     *  com.oriente.aptsample.MyObject object internals:
     * OFF  SZ   TYPE DESCRIPTION               VALUE
     *   0   8        (object header: mark)     0x000000000000000d (biasable; age: 1)
     *   8   4        (object header: class)    0x0016a7b8
     *  12   4    int MyObject.a                1
     *  16   4    int MyObject.b                1
     *  20   4        (object alignment gap)
     * Instance size: 24 bytes
     * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
     *
     */
    @Test
    public void testObject() {
//        MyObject o = new MyObject();
//        String s = ClassLayout.parseInstance(o).toPrintable();
//        System.out.println(s);
    }
}