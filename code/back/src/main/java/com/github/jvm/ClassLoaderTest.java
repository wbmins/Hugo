package com.github.jvm;

public class ClassLoaderTest {
    public static void main(String[] args) {

        //获取系统类加载器
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(sysClassLoader);
        //jdk.internal.loader.ClassLoaders$AppClassLoader@512ddf17

        //获取上层 ，扩展类加载器 识别 /jre/lib/ext目录
        ClassLoader extClassLoader = sysClassLoader.getParent();
        System.out.println(extClassLoader);
        //jdk.internal.loader.ClassLoaders$PlatformClassLoader@35bbe5e8

        //获取上层 启动类加载器 c/c++实现 rt.jar resource.jar sun.boot.class.path下的内容
        ClassLoader bootstarpClassLoader = extClassLoader.getParent();
        System.out.println(bootstarpClassLoader);
        //null

        //获取用户自定义的类加载器,默认使用系统类加载器加载
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);
        //jdk.internal.loader.ClassLoaders$AppClassLoader@512ddf17

        //String 使用启动类加载器加载 --> java 的核心类库都是使用启动类加载器加载
        ClassLoader string_classLoader = String.class.getClassLoader();
        System.out.println(string_classLoader);
        //null

        //获取classloader 四种途径

        //获取当前类的
        //Class.forName().getClassLoader()

        //获取线程上下文
        //Thread.currentThread().getContextClassLoader();

        //获取系统的
        //ClassLoader.getSystemClassLoader();

        //获取调用着

        /**
         * 双亲委派机制
         *      如果一个类收到类的加载请求，他不会自己加载，而是请求委托给父类加载器加载
         *      如果父类加载其还存在父类加载器则继续向上委托
         *      如果父类加载器可以加载就成功返回，否则子类加载器才会加载
         *
         * 优势
         *      避免类的重复加载
         *      保护程序安全防止核心api被修改
         */
    }
}
