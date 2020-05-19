package Nio

import org.junit.Test
import java.nio.ByteBuffer

/**
 * @Classname TestBuffer
 * @Description buffer
 * @Date 2020/3/27 下午10:15
 * @Created by pluto
 */
class TestBuffer {
    /**
     * 1.缓冲区
     * ByteBuffer
     * CharBuffer
     * ShortBuffer
     * IntBuffer
     * LongBuffer
     * FloatBuffer
     * DoubleBuffer
     * 同意通过allocate()获取
     * 2.缓冲区存取数据核心方法
     * put()
     * get()
     * 3.四个核心属性
     * capacity:缓冲区最大存储数量,一旦声明不能改变
     * limit:表示缓冲区可以操作数据的大小
     * position:表示缓冲区正在操作数据的位置
     * 0 <= position <= limit <= capacity
     * mark:标记position位置通过reset()回答mark位置
     * 4.直接缓冲区于非直接缓冲区
     * 非直接缓冲区:通过allocate方法分配,建立在jvm内存中
     * 直接缓冲区:通过allocateDirect()分配,建立在物理内存中,
     * 可以提高效率
     */
    @Test
    fun test() {
        val str = "qwerty"
        val byteBuffer = ByteBuffer.allocate(1024)
        println("1.分配指定大小缓冲区---allocate---")
        println(byteBuffer.position())
        println(byteBuffer.limit())
        println(byteBuffer.capacity())
        byteBuffer.put(str.toByteArray())
        println("2.put存数据到缓冲区---put---")
        println(byteBuffer.position())
        println(byteBuffer.limit())
        println(byteBuffer.capacity())
        byteBuffer.flip()
        println("3.切换读数据模式---flip---")
        println(byteBuffer.position())
        println(byteBuffer.limit())
        println(byteBuffer.capacity())
        val bytes = ByteArray(byteBuffer.limit())
        byteBuffer[bytes]
        println(String(bytes, 0, bytes.size))
        println("4.get读取数据---get---")
        println(byteBuffer.position())
        println(byteBuffer.limit())
        println(byteBuffer.capacity())
        byteBuffer.rewind()
        println("5.可重复读---rewind---")
        println(byteBuffer.position())
        println(byteBuffer.limit())
        println(byteBuffer.capacity())
        byteBuffer.clear()
        //但数据还在,处于被遗忘状态
        println("6.清除缓冲区---clear---")
        println(byteBuffer.position())
        println(byteBuffer.limit())
        println(byteBuffer.capacity())
    }
}
