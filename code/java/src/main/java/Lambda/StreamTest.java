package Lambda;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author pluto
 * @date 2020/3/18 下午1:08
 */
public class StreamTest {

    public static List<User> getUsers(){
        List<User> list = new ArrayList<>();
        list.add(new User("AA",12));
        list.add(new User("BBBB",15));
        list.add(new User("C",13));
        list.add(new User("DDD",19));
        list.add(new User("EE",15));
        list.add(new User("FFFFF",14));
//        list.add(new User("F",14));
//        list.add(new User("F",14));
//        list.add(new User("F",14));
//        list.add(new User("F",14));
        return list;
    }


    public void test1(){
        //1.集合创建Stream
        Stream<User> st = getUsers().stream(); //返回一个顺序流(拿数据按照顺序拿)
        Stream<User> st1 = getUsers().parallelStream(); //返回一个并行流(并行拿)
        //2.数组创建Stream
        int[] arr = new int[]{1,2,3,4};
        IntStream intStream = Arrays.stream(arr);
        //3.通过stream的of
        Stream<Integer> stream = Stream.of(1,2,3,4);
        //4.创建无限流
        //迭代 遍历前十个偶数
        Stream.iterate(0,t -> t + 2).limit(10).forEach(System.out::println);
        // 生成 十个随机数
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
    }

    public void test2(){
        //filter() 接受Lambda,从流中排除某些元素
        Stream<User> stream1 = getUsers().stream();
        stream1.filter(e -> e.getAge() > 15).forEach(System.out::println);
        //limit() 截断,是元素不超过给定数量
        Stream<User> stream2 = getUsers().stream();
        stream2.limit(10).forEach(System.out::println);
        //skip()跳过前n个元素
        Stream<User> stream3 = getUsers().stream();
        stream3.skip(4).forEach(System.out::println);
        //distinct()筛选 更具hashcode和equals去重
        Stream<User> stream4 = getUsers().stream();
        stream4.distinct().forEach(System.out::println);
    }

    public void test4(){
        //映射
        List<String > list = Arrays.asList("aa","bb","cc","dd");
        list.stream().map(str -> str.toUpperCase()).forEach(System.out::println);
        list.stream().map(String::toUpperCase).forEach(System.out::println);

        //获取员工姓名长度大于三的
        Stream<User> stream = getUsers().stream();
        //先映射得到名字
        Stream<String> stringStream = stream.map(User::getName);
        //过滤长度大于三的
        stringStream.filter(name ->name.length() > 3).forEach(System.out::println);

        Stream<Character> stream1 = list.stream().flatMap(StreamTest::strTostream);
        stream1.forEach(System.out::println);
    }

    public static Stream<Character> strTostream(String str){
        List<Character> list = new ArrayList<>();
        for(Character c: str.toCharArray()){
            list.add(c);
        }
        return list.stream();
    }
}

class User{
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}