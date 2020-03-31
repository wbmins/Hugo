package Lambda;

import org.junit.Test;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author pluto
 * @date 2020/3/18 上午10:38
 */
public class LambdaTest {

    @Test
    public void test1(){
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("java8之前语法");
            }
        };
        r1.run();
        System.out.println("**********************");
        Runnable r2 = () -> System.out.println("java8之后语法");
        r2.run();
    }

    @Test
    public void test2(){
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        System.out.println("java8之前语法"+comparator.compare(12,31));
        System.out.println("**********************");
        //Lambda表达式写法
        Comparator<Integer> comparator2 = ( o1,  o2) -> o1.compareTo(o2);
        System.out.println("java8之前语法"+comparator2.compare(64,31));
        System.out.println("**********************");
        //方法引用
        Comparator<Integer> comparator3 = Integer :: compareTo;
        System.out.println("java8之前语法"+comparator2.compare(64,31));

        Consumer<String> con = (String s) -> {
            System.out.println("hello");
        };
    }

    public List<String> filterString(List<String> list, Predicate<String> pre){
        ArrayList<String> list1 = new ArrayList<>();

        for (String str : list){
            if(pre.test(str)){
                list1.add(str);
            }
        }
        return list1;
    }

    @Test
    public void test3(){
        List<String> list = Arrays.asList("南京", "天京");
        List<String> filterstr = filterString(list, new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("京");
            }
        });
        System.out.println(filterstr);
        List<String> filterstr2 = filterString(list,s -> s.contains("京"));
    }

    public void test4(){
        Consumer<String> con = str -> System.out.println(str);
        con.accept("hello");
        Consumer<String> con1 = System.out::println;
        con1.accept("world");
        Comparator<Integer> com = Integer::compareTo;

        Comparator<String> comp = (o1,o2) -> o1.compareTo(o2);

        Comparator<String> compa = String::compareTo;
    }

    @Test
    public void test5(){
        Function<Integer,String[]> fun = length -> new String[length];
        fun.apply(5);
        System.out.println(fun.toString());

        Function<Integer,String[]> fun2 = String[] :: new;
    }

    @Test
    public void test(){
//
//        File sys = new File("/home/pluto/Download/1.jpg");
//        System.out.println(sys.canRead()+" "+sys.canWrite());

    }

}