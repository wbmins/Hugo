package Juc.lock

import org.junit.Test

/**
 * @Classname Lock_
 * @Description 生产消费者第一个版本
 * @Date 2020/3/27 下午6:54
 * @Created by pluto
 */
internal class MyData1 {
    private var num = 0
    private val lock = java.lang.Object()

    @Synchronized
    fun inc() {
        try {
            while (num != 0) {
//                this.wait() //1. 等待不能生产
                lock.wait()
            }
            num++ //2. 生产
            println(Thread.currentThread().name +
                    "我是生产者,正在生产")
//            this.notifyAll() //3. 通知
            lock.notifyAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun dec() {
        try {
            while (num == 0) {
//                this.wait() //1.等待不能消费
                lock.wait()
            }
            num-- //2. 消费
            println(Thread.currentThread().name +
                    "我是消费者,正在消费")
//            this.notifyAll()
            lock.notifyAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

class Lock_V1 {
    /**
     * 传统的生产者消费者第一个版本
     */
    @Test
    fun tradtional() {
        val myData = MyData1()
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
