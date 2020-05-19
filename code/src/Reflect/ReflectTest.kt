package Reflect;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @Classname ReflectTest
 * @Description reflect 反射
 * @Date 2020/3/30 下午5:10
 * @Created by pluto
 */
class Person{
    private String name;
    private Integer age;
}
public class ReflectTest {
    /**
     * Class类	代表类的实体，在运行的Java应用程序中表示类和接口
     * Field类	代表类的成员变量（成员变量也称为类的属性）
     * Method类	代表类的方法
     * Constructor类	代表类的构造方法
     */


    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz = Person.class;
        Method method = clazz.getDeclaredMethod("aaaa",String.class);
        method.setAccessible(true);
        method.invoke(Person.class,"hahhah");
    }
    //反射和面向对象是否矛盾怎么理解
    //不矛盾,面相对是建议怎么用,反射是调用的事

    @Test
    public void test0() throws ClassNotFoundException {
        //怎么获取Class实例
        //方式１调用运行时类的属性
        Class clazz1 = Person.class;
        //方式2运行时类的对象
        Person person = new Person();
        Class clazz2 = person.getClass();
        //方式3Class的静态方法　Class.forName()(用的多)
        Class clazz3 = Class.forName("Reflect.Person");
        //方式4 classloader
        ClassLoader classLoader = ReflectTest.class.getClassLoader();
        Class clazz4 = classLoader.loadClass("Reflect.Person");
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
    public void test1() throws IOException {
        //获取配置文件
        Properties pro = new Properties();
        //此时文件默认在当前model下
        //读取配置文件方式１
//        FileInputStream file = new FileInputStream("test.properties");
//        pro.load(file);
        //读取配置文件方式２
        //此时文件默认在当前src下
        ClassLoader classLoader = ReflectTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("test.properties");
        pro.load(inputStream);

        String name = pro.getProperty("name");
        String age = pro.getProperty("age");
        System.out.println(name + age);

    }
}
