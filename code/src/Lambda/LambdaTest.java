package Lambda;

import org.junit.Test;

/**
 * @Classname LambdaTest
 * @Description 线程和并发
 * @Date 2020/3/19 下午5:21
 * @Created by pluto
 */
public class LambdaTest {

    @Test
    public void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hahhahhaha");
            }
        }, "aaaaa").start();

        new Thread(() -> System.out.println("hahha"), "aaa").start();
    }
}
