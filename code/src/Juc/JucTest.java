package Juc;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Classname JucTest
 * @Description JUC
 * @Date 2020/3/27 上午11:00
 * @Created by pluto
 */

class MyThread implements Runnable {
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("------------");
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

class MyThread1 implements Runnable {
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void run() {
        System.out.println(getAtomicInteger());
    }

    public int getAtomicInteger() {
        return atomicInteger.incrementAndGet();
    }
}

public class JucTest {
    @Test
    public void test() {
        MyThread myThread = new MyThread();
        new Thread(myThread).start();
        while (true) {
            synchronized (myThread) {
                if (myThread.isFlag()) {
                    System.out.println("1212121212121");
                    break;
                }
            }
        }
    }

    @Test
    public void test2() {
        MyThread myThread = new MyThread();
        new Thread(myThread).start();
        while (true) {
            if (myThread.isFlag()) {
                System.out.println("1212121212121");
                break;
            }
        }
    }

    @Test
    public void test3() {
        MyThread1 myThread1 = new MyThread1();
        for (int i = 0; i < 10; i++) {
            new Thread(myThread1).start();
        }
    }

    @Test
    public void test4() {
        List<Integer> list = new CopyOnWriteArrayList<>();
    }
}
