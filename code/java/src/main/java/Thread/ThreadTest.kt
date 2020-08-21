package Thread

import org.junit.Test
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * @Classname ThreadTest
 * @Description 线程相关
 * @Date 2020/3/21 下午4:48
 * @Created by pluto
 */
internal class MyThread1 : Thread() {
    override fun run() {
        super.run()
        println(currentThread().name +
                "我是继承Thread类创建线程")
    }
}

internal class MyThread2 : Runnable {
    override fun run() {
        println(Thread.currentThread().name +
                "我是实现Runnable接口创建线程")
    }
}

internal class MyThread3 : Callable<Int> {
    @Throws(Exception::class)
    override fun call(): Int {
        println(Thread.currentThread().name +
                "我是实现Callable接口创建线程")
        return Integer.valueOf("12")
    }
}

internal class MyData {
    private var num = 0
    private val lock: Lock = ReentrantLock()
    private val condition = lock.newCondition()
    fun inc() {
        lock.lock()
        try {
            while (num != 0) {
                condition.await() //1. 等待不能生产
            }
            num++ //2. 生产
            println(Thread.currentThread().name +
                    "我是生产者,正在生产")
            condition.signalAll() //3. 通知
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }

    fun dec() {
        lock.lock()
        try {
            while (num == 0) {
                condition.await() //1.等待不能消费
            }
            num-- //2. 消费
            println(Thread.currentThread().name +
                    "我是消费者,正在消费")
            condition.signalAll()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }
}

internal class MyDataN(blockingQueue: BlockingQueue<String>?) {
    @Volatile
    private var FLAG = true
    private val atomicInteger = AtomicInteger()
    var blockingQueue: BlockingQueue<String>? = null

    @Throws(Exception::class)
    fun myProd() {
        var data: String? = null
        var retValue: Boolean
        while (FLAG) {
            data = atomicInteger.incrementAndGet().toString() + ""
            retValue = blockingQueue!!.offer(data, 2L, TimeUnit.SECONDS)
            if (retValue) {
                println(Thread.currentThread().name +
                        "插入队列" + data + "成功")
            } else {
                println(Thread.currentThread().name +
                        "插入队列" + data + "失败")
            }
            TimeUnit.SECONDS.sleep(1)
        }
        println(Thread.currentThread().name +
                "大老板叫停")
    }

    @Throws(Exception::class)
    fun myConsumer() {
        var result: String? = null
        while (FLAG) {
            result = blockingQueue!!.poll(2L, TimeUnit.SECONDS)
            if (null == result || result.equals("", ignoreCase = true)) {
                println(Thread.currentThread().name +
                        "超过2秒钟没有渠道蛋糕,消费退出")
                return
            }
            println(Thread.currentThread().name +
                    "消费数据" + result + "成功")
        }
    }

    fun stop() {
        FLAG = false
    }

    init {
        this.blockingQueue = blockingQueue
    }
}

class ThreadTest {
    /**
     * 现在阻塞队列版生产消费
     */
    @Test
    fun now() {
        val myDataN = MyDataN(ArrayBlockingQueue(10))
        Thread(Runnable {
            try {
                myDataN.myProd()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, "AA").start()
        Thread(Runnable {
            try {
                myDataN.myConsumer()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, "BB").start()
        try {
            TimeUnit.SECONDS.sleep(5)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("5秒结束,main打老板叫停")
    }

    /**
     * 传统的生产者消费者
     */
    @Test
    fun tradtional() {
        val myData = MyData()
        Thread(Runnable {
            for (i in 0..4) {
                try {
                    myData.inc()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, "AA").start()
        Thread(Runnable {
            for (i in 0..4) {
                try {
                    myData.dec()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, "BB").start()
    }

    /**
     * 4.3 一池带缓冲的多线程(底层 SynchronousQueue<Runnable>()) 优势: 执行短期异步小程序或负载较轻的服务
    </Runnable> */
    @Test
    fun test4_3() {
        val executorService3 = Executors.newCachedThreadPool()
        try {
            for (i in 0..9) {
                executorService3.execute {
                    println(Thread.currentThread().name +
                            "带缓冲的线程池中的线程")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            executorService3.shutdown()
        }
    }

    /**
     * 4.2 一池五个处理线程(固定数目的线程池 底层BlockingQueue<Runnable>) 优势: 执行长期任务性能比较好
    </Runnable> */
    @Test
    fun test4_2() {
        val executorService2 = Executors.newFixedThreadPool(5)
        try {
            for (i in 0..5) {
                executorService2.execute {
                    println(Thread.currentThread().name +
                            "我是固定数目线程池中的线程")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            executorService2.shutdown()
        }
    }

    /**
     * 4.1 一池单线程(底层 BlockingQueue<Runnable>) 优势: 一个任务一个任务执行场景
    </Runnable> */
    @Test
    fun test4_1() {
        val executorService1: ExecutorService = Executors.newSingleThreadScheduledExecutor()
        executorService1.execute { println(Thread.currentThread().name + "我是单线程池中的线程") }
        try {
            executorService1.execute {
                println(Thread.currentThread().name +
                        "我是线程池Lambda创建线程")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            executorService1.shutdown()
        }
    }

    /**
     * 3. 实现Callable接口重写call方法方法
     */
    @Test
    fun test3() {
        val myThread3 = MyThread3()
        val futureTask = FutureTask(myThread3)
        val thread = Thread(futureTask, "futureTask")
        thread.start()
        try {
            while (!futureTask.isDone) { //等算完再取值
            }
            println("********result+" + futureTask.get()) //方法尽量往后放
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 2. 实现Runnable接口重写run方法
     */
    @Test
    fun test2() {
        val myThread2 = MyThread2()
        myThread2.run()
    }

    /**
     * 1. 继承thread类重写run方法
     */
    @Test
    fun test1() {
        val myThread1 = MyThread1()
        myThread1.start()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            test()
        }

        /**
         * SynchronousQueue测试
         * 该方法需要在main里面
         */
        fun test() {
            val blockingQueue: BlockingQueue<String> = SynchronousQueue()
            Thread(Runnable {
                try {
                    println(Thread.currentThread().name +
                            "------put 1")
                    blockingQueue.put("1")
                    println(Thread.currentThread().name +
                            "------put 2")
                    blockingQueue.put("2")
                    println(Thread.currentThread().name +
                            "------put 3")
                    blockingQueue.put("3")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }, "AAA").start()
            Thread(Runnable {
                try {
                    try {
                        TimeUnit.SECONDS.sleep(3)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    println(Thread.currentThread().name +
                            "------" + blockingQueue.take())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                try {
                    try {
                        TimeUnit.SECONDS.sleep(3)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    println(Thread.currentThread().name +
                            "------" + blockingQueue.take())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                try {
                    try {
                        TimeUnit.SECONDS.sleep(3)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    println(Thread.currentThread().name +
                            "------" + blockingQueue.take())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }, "BBB").start()
        }
    }
}
