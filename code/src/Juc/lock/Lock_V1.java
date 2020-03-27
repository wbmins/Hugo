package Juc.lock;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname Lock_
 * @Description 生产消费者第一个版本
 * @Date 2020/3/27 下午6:54
 * @Created by pluto
 */
class MyData1{
    private int num = 0;
    public synchronized void inc(){
        try{
            while (num != 0){
                this.wait(); //1. 等待不能生产
            }
            num ++; //2. 生产
            System.out.println(Thread.currentThread().getName()+
                    "我是生产者,正在生产");
           this.notifyAll(); //3. 通知
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public synchronized void dec(){
        try{
            while (num == 0){
                this.wait(); //1.等待不能消费
            }
            num --; //2. 消费
            System.out.println(Thread.currentThread().getName()+
                    "我是消费者,正在消费");
            this.notifyAll();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
public class Lock_V1 {
    /**
     * 传统的生产者消费者第一个版本
     */
    @Test
    public void tradtional() {
        MyData1 myData = new MyData1();
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
