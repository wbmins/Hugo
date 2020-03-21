package Thread;

import com.sun.jmx.snmp.ThreadContext;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @Classname ThreadTest
 * @Description 线程相关
 * @Date 2020/3/21 下午4:48
 * @Created by pluto
 */

class MyThread1 extends Thread{
    @Override
    public void run() {
        super.run();
        System.out.println(Thread.currentThread().getName()+
                "我是继承Thread类创建线程");
    }
}
class MyThread2 implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+
                "我是实现Runnable接口创建线程");
    }
}
class MyThread3 implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+
                "我是实现Callable接口创建线程");
        return Integer.valueOf("12");
    }
}
public class ThreadTest {

    @Test
    public void test(){
        test1();
        test2();
        test3();
        //线程池创建线程
        test4_1();
        System.out.println();
        test4_2();
        System.out.println();
        test4_3();

    }

    public void test4_3() {
        //3. 一池带缓冲的多线程(底层 SynchronousQueue<Runnable>()) 优势: 执行短期异步小程序或负载较轻的服务
        ExecutorService executorService3 = Executors.newCachedThreadPool();
        try{
            for (int i = 0; i < 10; i++) {
                executorService3.execute(() -> System.out.println(Thread.currentThread().getName()+
                        "带缓冲的线程池中的线程"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService3.shutdown();
        }
    }

    public void test4_2() {
        //2. 一池五个处理线程(固定数目的线程池 底层BlockingQueue<Runnable>) 优势: 执行长期任务性能比较好
        ExecutorService executorService2 = Executors.newFixedThreadPool(5);
        try{
            for (int i = 0; i < 6; i++) {
                executorService2.execute(() -> System.out.println(Thread.currentThread().getName()+
                        "我是固定数目线程池中的线程"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService2.shutdown();
        }
    }

    public void test4_1() {
        //1. 一池单线程(底层 BlockingQueue<Runnable>) 优势: 一个任务一个任务执行场景
        ExecutorService executorService1 = Executors.newSingleThreadScheduledExecutor();
        executorService1.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"我是单线程池中的线程");
            }
        });
        try{
            executorService1.execute(() -> System.out.println(Thread.currentThread().getName()+
                    "我是线程池Lambda创建线程"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService1.shutdown();
        }
    }

    public void test3() {
        //实现Callable接口重写call方法方法
        MyThread3 myThread3 = new MyThread3();
        FutureTask<Integer> futureTask = new FutureTask<>(myThread3);
        Thread thread = new Thread(futureTask,"futureTask");
        thread.start();
        try {
            while (!futureTask.isDone()){ //等算完再取值
            }
            System.out.println("********result+" + futureTask.get()); //方法尽量往后放
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void test2() {
        //实现Runnable接口重写run方法
        MyThread2 myThread2 = new MyThread2();
        myThread2.run();
    }

    public void test1() {
        //继承thread类重写run方法
        MyThread1 myThread1 = new MyThread1();
        myThread1.start();
    }
}
