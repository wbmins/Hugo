package Juc.lock

import org.junit.Test
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * @Classname Lock_V2
 * @Date 2020/3/27 下午6:59
 * @Created by pluto
 */
internal class MyData2 {
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

class Lock_V2 {
    /**
     * 传统的生产者消费者第二个版本
     */
    @Test
    fun tradtional() {
        val myData = MyData2()
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
}
