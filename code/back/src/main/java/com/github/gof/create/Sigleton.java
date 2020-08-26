package com.github.gof.create;

/**
 * ①饿汉式
 * 是否 Lazy 初始化：否
 * 是否多线程安全：是
 * 实现难度：易
 */
class Sigleton1 {
    public static final Sigleton1 INSTANCE = new Sigleton1();

    private Sigleton1() {
    }

    public static Sigleton1 getInstance() {
        return INSTANCE;
    }
}

/**
 * ②枚举:表示该类型的对象是有限的
 * 是否 Lazy 初始化：否
 * 是否多线程安全：是
 * 实现难度：易
 */
enum Sigleton2 {
    INSTANCE
}

/**
 * ③懒汉式
 * 是否 Lazy 初始化：是
 * 是否多线程安全：否
 * 实现难度：易
 */
class Sigleton3 {
    private static Sigleton3 instance;

    private Sigleton3() {
    }

    public static Sigleton3 getInstance() {
        if (instance == null)
            instance = new Sigleton3();
        return instance;
    }
}

/**
 * ④懒汉式 静态代码块 (适合复杂实例)
 * 是否 Lazy 初始化：是
 * 是否多线程安全：是
 * 实现难度：一般
 */
class Sigleton4 {
    private static final Sigleton4 INSTANCE;

    static {
        INSTANCE = new Sigleton4();
    }

    private Sigleton4() {
    }

    public static Sigleton4 getInstance() {
        return INSTANCE;
    }
}


/**
 * ⑤懒汉式
 * 是否 Lazy 初始化：是
 * 是否多线程安全：是
 * 实现难度：易
 */
class Sigleton5_1 {
    private static Sigleton5_1 instance;

    private Sigleton5_1() {
    }

    public static Sigleton5_1 getInstance() {
        synchronized (Sigleton4.class) {
            if (instance == null)
                instance = new Sigleton5_1();
            return instance;
        }
    }
}

//优化后
class Sigleton5_2 {
    private static Sigleton5_2 instance;

    private Sigleton5_2() {
    }

    //DCL double check lock 双端检锁机制
    public static Sigleton5_2 getInstance() {
        if (instance == null) {
            synchronized (Sigleton5_2.class) {
                if (instance == null)
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

    private Sigleton5_3() {
    }

    //DCL double check lock 双端检锁机制
    public static Sigleton5_3 getInstance() {
        if (instance == null) {
            synchronized (Sigleton5_3.class) {
                if (instance == null)
                    instance = new Sigleton5_3();
            }
        }
        return instance;
    }
}

/**
 * ⑥静态内部类
 * 是否 Lazy 初始化：是
 * 是否多线程安全：是
 * 实现难度：一般
 * 在内部类加载时才创建对象
 * 静态内部类不会随着外部类初始化
 * 而初始化的,他是要单独加载和初始化的
 */
class Sigleton6 {
    private Sigleton6() {
    }

    private static class Inner {
        public static final Sigleton6 INSTANCE = new Sigleton6();
    }

    public static Sigleton6 getInstance() {
        return Inner.INSTANCE;
    }
}

public class Sigleton {
}
