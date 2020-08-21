package Reflect

import org.junit.Test
import java.io.IOException
import java.lang.reflect.InvocationTargetException
import java.util.*

/**
 * @Classname ReflectTest
 * @Description reflect 反射
 * @Date 2020/3/30 下午5:10
 * @Created by pluto
 */
internal class Person {
    private val name: String? = null
    private val age: Int? = null
}

class ReflectTest {
    /**
     * Class类	代表类的实体，在运行的Java应用程序中表示类和接口
     * Field类	代表类的成员变量（成员变量也称为类的属性）
     * Method类	代表类的方法
     * Constructor类	代表类的构造方法
     */
    @Test
    @Throws(NoSuchMethodException::class, InvocationTargetException::class, IllegalAccessException::class)
    fun test() {
        val clazz: Class<*> = Person::class.java
        val method = clazz.getDeclaredMethod("aaaa", String::class.java)
        method.isAccessible = true
        method.invoke(Person::class.java, "hahhah")
    }

    //反射和面向对象是否矛盾怎么理解
    //不矛盾,面相对是建议怎么用,反射是调用的事
    @Test
    @Throws(ClassNotFoundException::class)
    fun test0() {
        //怎么获取Class实例
        //方式１调用运行时类的属性
        val clazz1: Class<*> = Person::class.java
        //方式2运行时类的对象
        val person = Person()
        val clazz2: Class<*> = person.javaClass
        //方式3Class的静态方法　Class.forName()(用的多)
        val clazz3 = Class.forName("Reflect.Person")
        //方式4 classloader
        val classLoader = ReflectTest::class.java.classLoader
        val clazz4 = classLoader.loadClass("Reflect.Person")
        //class包括那些
        // 外部类　成员　局部类　匿名内部类
        //接口
        //数组
        //枚举
        //注解
        //基本数据类型
        //void
    }

    @Test
    @Throws(IOException::class)
    fun test1() {
        //获取配置文件
        val pro = Properties()
        //此时文件默认在当前model下
        //读取配置文件方式１
//        FileInputStream file = new FileInputStream("test.properties");
//        pro.load(file);
        //读取配置文件方式２
        //此时文件默认在当前src下
        val classLoader = ReflectTest::class.java.classLoader
        val inputStream = classLoader.getResourceAsStream("test.properties")
        pro.load(inputStream)
        val name = pro.getProperty("name")
        val age = pro.getProperty("age")
        println(name + age)
    }
}
