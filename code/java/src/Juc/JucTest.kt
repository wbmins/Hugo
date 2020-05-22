package Juc

import org.junit.Test
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

/**
 * @Classname JucTest
 * @Description JUC
 * @Date 2020/3/27 上午11:00
 * @Created by pluto
 */
internal class MyThread : Runnable {
    @Volatile
    var isFlag = false
    override fun run() {
        try {
            Thread.sleep(200)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        isFlag = true
        println("------------")
    }

}

internal class MyThread1 : Runnable {
    private val atomicInteger = AtomicInteger(0)
    override fun run() {
        println(getAtomicInteger())
    }

    fun getAtomicInteger(): Int {
        return atomicInteger.incrementAndGet()
    }
}

class JucTest {
    @Test
    fun test() {
        val myThread = MyThread()
        Thread(myThread).start()
        while (true) {
            @JvmStatic
            if (myThread.isFlag) {
                println("1212121212121")
                break
            }
        }
    }

    @Test
    fun test2() {
        val myThread = MyThread()
        Thread(myThread).start()
        while (true) {
            if (myThread.isFlag) {
                println("1212121212121")
                break
            }
        }
    }

    @Test
    fun test3() {
        val myThread1 = MyThread1()
        for (i in 0..9) {
            Thread(myThread1).start()
        }
    }

    @Test
    fun test4() {
        val list: List<Int> = CopyOnWriteArrayList()
    }
}
