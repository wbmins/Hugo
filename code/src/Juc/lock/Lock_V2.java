package Juc.lock;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname Lock_V2
 * @Description TODO
 * @Date 2020/3/27 下午6:59
 * @Created by pluto
 */
class MyData2{
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void inc(){
        lock.lock();
        try{
            while (num != 0){
                condition.await(); //1. 等待不能生产
            }
            num ++; //2. 生产
            System.out.println(Thread.currentThread().getName()+
                    "我是生产者,正在生产");
            condition.signalAll(); //3. 通知
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void dec(){
        lock.lock();
        try{
            while (num == 0){
                condition.await(); //1.等待不能消费
            }
            num --; //2. 消费
            System.out.println(Thread.currentThread().getName()+
                    "我是消费者,正在消费");
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

public class Lock_V2 {
    /**
     * 传统的生产者消费者第二个版本
     */
    @Test
    public void tradtional() {
        MyData2 myData = new MyData2();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try{
                        myData.inc();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        },"AA").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try{
                        myData.dec();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        },"BB").start();
    }
}
