package Juc.lock

import java.util.concurrent.BlockingQueue
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.TimeUnit

/**
 * @Classname Lock_V3
 * @Date 2020/3/27 下午7:01
 * @Created by pluto
 */
object Lock_V3 {
    @JvmStatic
    fun main(args: Array<String>) {
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
