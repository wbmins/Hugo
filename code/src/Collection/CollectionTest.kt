package Collection

import org.junit.Test
import java.util.*

/**
 * @Classname CollectionTest
 * @Description 集合知识整理
 * @Date 2020/3/24 下午3:47
 * @Created by pluto
 */
internal class Person {
    var id: Int? = null
    var name: String? = null

    constructor()
    constructor(id: Int?, name: String?) {
        this.id = id
        this.name = name
    }

    override fun hashCode(): Int {
        var result = if (id != null) id.hashCode() else 0
        result = 31 * result + if (name != null) name.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}'
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }
}

class CollectionTest {
    @Test
    fun test() {
        val hashSet: HashSet<Person> = HashSet<Person>()
        val p1: Person = Person(1001, "AA")
        val p2: Person = Person(1002, "BB")
        hashSet.add(p1)
        hashSet.add(p2)
        println(hashSet)
        p1.name = "CC"
        hashSet.remove(p1)
        println(hashSet)
        hashSet.add(Person(1001, "CC"))
        println(hashSet)
        hashSet.add(Person(1001, "AA"))
        println(hashSet)
    }
}
