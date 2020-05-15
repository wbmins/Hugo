package Thread;


import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * @Classname ThreadTest
 * @Description 线程相关
 * @Date 2020/3/21 下午4:48
 * @Created by pluto
 */

class MyThread1 extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println(Thread.currentThread().getName() +
                "我是继承Thread类创建线程");
    }
}

class MyThread2 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() +
                "我是实现Runnable接口创建线程");
    }
}

class MyThread3 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() +
                "我是实现Callable接口创建线程");
        return Integer.valueOf("12");
    }
}

class MyData {
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void inc() {
        lock.lock();
        try {
            while (num != 0) {
                condition.await(); //1. 等待不能生产
            }
            num++; //2. 生产
            System.out.println(Thread.currentThread().getName() +
                    "我是生产者,正在生产");
            condition.signalAll(); //3. 通知
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void dec() {
        lock.lock();
        try {
            while (num == 0) {
                condition.await(); //1.等待不能消费
            }
            num--; //2. 消费
            System.out.println(Thread.currentThread().getName() +
                    "我是消费者,正在消费");
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class MyDataN {
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public MyDataN(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void myProd() throws Exception {
        String data = null;
        boolean retValue;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() +
                        "插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() +
                        "插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() +
                "大老板叫停");
    }

    public void myConsumer() throws Exception {
        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")) {
                System.out.println(Thread.currentThread().getName() +
                        "超过2秒钟没有渠道蛋糕,消费退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() +
                    "消费数据" + result + "成功");
        }
    }

    public void stop() {
        this.FLAG = false;
    }
}

public class ThreadTest {
    public static void main(String[] args) {
        test();
    }

    /**
     * SynchronousQueue测试
     * 该方法需要在main里面
     */
    public static void test() {
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

    /**
     * 现在阻塞队列版生产消费
     */
    @Test
    public void now() {
        MyDataN myDataN = new MyDataN(new ArrayBlockingQueue<String>(10));
        new Thread(() -> {
            try {
                myDataN.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AA").start();
        new Thread(() -> {
            try {
                myDataN.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BB").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("5秒结束,main打老板叫停");
    }

    /**
     * 传统的生产者消费者
     */
    @Test
    public void tradtional() {
        MyData myData = new MyData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        myData.inc();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "AA").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        myData.dec();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "BB").start();
    }

    /**
     * 4.3 一池带缓冲的多线程(底层 SynchronousQueue<Runnable>()) 优势: 执行短期异步小程序或负载较轻的服务
     */
    @Test
    public void test4_3() {
        ExecutorService executorService3 = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                executorService3.execute(() -> System.out.println(Thread.currentThread().getName() +
                        "带缓冲的线程池中的线程"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService3.shutdown();
        }
    }

    /**
     * 4.2 一池五个处理线程(固定数目的线程池 底层BlockingQueue<Runnable>) 优势: 执行长期任务性能比较好
     */
    @Test
    public void test4_2() {
        ExecutorService executorService2 = Executors.newFixedThreadPool(5);
        try {
            for (int i = 0; i < 6; i++) {
                executorService2.execute(() -> System.out.println(Thread.currentThread().getName() +
                        "我是固定数目线程池中的线程"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService2.shutdown();
        }
    }

    /**
     * 4.1 一池单线程(底层 BlockingQueue<Runnable>) 优势: 一个任务一个任务执行场景
     */
    @Test
    public void test4_1() {
        ExecutorService executorService1 = Executors.newSingleThreadScheduledExecutor();
        executorService1.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "我是单线程池中的线程");
            }
        });
        try {
            executorService1.execute(() -> System.out.println(Thread.currentThread().getName() +
                    "我是线程池Lambda创建线程"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService1.shutdown();
        }
    }

    /**
     * 3. 实现Callable接口重写call方法方法
     */
    @Test
    public void test3() {
        MyThread3 myThread3 = new MyThread3();
        FutureTask<Integer> futureTask = new FutureTask<>(myThread3);
        Thread thread = new Thread(futureTask, "futureTask");
        thread.start();
        try {
            while (!futureTask.isDone()) { //等算完再取值
            }
            System.out.println("********result+" + futureTask.get()); //方法尽量往后放
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2. 实现Runnable接口重写run方法
     */
    @Test
    public void test2() {
        MyThread2 myThread2 = new MyThread2();
        myThread2.run();
    }

    /**
     * 1. 继承thread类重写run方法
     */
    @Test
    public void test1() {
        MyThread1 myThread1 = new MyThread1();
        myThread1.start();
    }
}
