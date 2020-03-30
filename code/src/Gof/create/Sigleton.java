package Gof.create;

/**
 * @Classname Sigtone
 * @Description 单例模式
 * @Date 2020/3/30 下午6:05
 * @Created by pluto
 */
/**
 * 饿汉式(直接创建不管你需不需要)
 * 构造器私有化
 * 自行创建并用静态变量保存
 * 向外提供这个实例
 * 强调这是一个单例可以用final修饰
 */
class Sigleton1 {
    public static final Sigleton1 INSTANCE = new Sigleton1();
    private Sigleton1(){
    }
    public static Sigleton1 getInstance(){
        return INSTANCE;
    }
}
/**
 * 枚举:表示该类型的对象是有限的
 * 我们可以限定为一个就成为单例
 */
enum  Sigleton2 {
    INSTANCE
}

/**
 * 静态代码块 (适合复杂实例)
 */
class Sigleton3 {
    private static final Sigleton3 INSTANCE;
    static {
        INSTANCE = new Sigleton3();
    }
    private Sigleton3(){
    }
    public static Sigleton3 getInstance(){
        return INSTANCE;
    }
}
/**
 * 构造器私有化
 * 自行创建并用静态变量保存
 * 向外提供这个实例
 * 线程不安全 (单线程)
 */
class Sigleton4 {
    private static Sigleton4 instance;
    private Sigleton4(){
    }
    public static Sigleton4 getInstance(){
        if(instance == null)
            instance = new Sigleton4();
        return instance;
    }
}

/**
 * 线程安全 (多线程)
 */
class Sigleton5_1 {
    private static  Sigleton5_1 instance;
    private Sigleton5_1(){
    }
    public static Sigleton5_1 getInstance(){
        synchronized (Sigleton4.class){
            if(instance == null)
                instance = new Sigleton5_1();
            return instance;
        }
    }
}
//优化后
class Sigleton5_2 {
    private static  Sigleton5_2 instance;
    private Sigleton5_2(){
    }
    //DCL double check lock 双端检锁机制
    public static Sigleton5_2 getInstance(){
        if(instance == null){
            synchronized (Sigleton5_2.class){
                if(instance == null)
                    instance = new Sigleton5_2();
            }
        }
        return instance;
    }
}
//更严格
class Sigleton5_3 {
    //volatile禁止指令重排
    private static volatile Sigleton5_3 instance;
    private Sigleton5_3(){
    }
    //DCL double check lock 双端检锁机制
    public static Sigleton5_3 getInstance(){
        if(instance == null){
            synchronized (Sigleton5_3.class){
                if(instance == null)
                    instance = new Sigleton5_3();
            }
        }
        return instance;
    }
}
//在内部类加载时才创建对象
//静态内部类不会随着外部类初始化而初始化的,他是要单独加载和初始化的
class Sigleton6 {
    private Sigleton6(){
    }
    private static class Inner{
        public static final Sigleton6 INSTANCE = new Sigleton6();
    }
    public static Sigleton6 getInstance(){
        return Inner.INSTANCE;
    }
}
public class Sigleton {
}
