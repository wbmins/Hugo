package Collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Classname CollectionTest
 * @Description 集合知识整理
 * @Date 2020/3/24 下午3:47
 * @Created by pluto
 */

class Person{
    Integer id;
    String name;

    public Person() {
    }

    public Person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!Objects.equals(id, person.id)) return false;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

public class CollectionTest {
    @Test
    public void test(){
        HashSet<Person> hashSet = new HashSet<Person>();
        Person p1 = new Person(1001,"AA");
        Person p2 = new Person(1002,"BB");
        hashSet.add(p1);
        hashSet.add(p2);
        System.out.println(hashSet);
        p1.name = "CC";
        hashSet.remove(p1);
        System.out.println(hashSet);
        hashSet.add(new Person(1001,"CC"));
        System.out.println(hashSet);
        hashSet.add(new Person(1001,"AA"));
        System.out.println(hashSet);
    }
}
