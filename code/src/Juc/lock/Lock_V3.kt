package Juc.lock;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Classname Lock_V3
 * @Description TODO
 * @Date 2020/3/27 下午7:01
 * @Created by pluto
 */
public class Lock_V3 {
    public static void main(String[] args) {

        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() +
                        "------put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() +
                        "------put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() +
                        "------put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();
        new Thread(() -> {
            try {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() +
                        "------" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() +
                        "------" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() +
                        "------" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }
}
