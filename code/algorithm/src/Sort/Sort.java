package Sort;

import java.util.Arrays;
import java.util.List;

public interface Sort {

    /**
     *
     * @param unsorted 一个可以排序的数组
     * @param <T>      泛型
     * @return         一个排好序列的数组
     */
    <T extends Comparable<T>> T[] sort(T[] unsorted);

    @SuppressWarnings("unchecked")
    default <T extends Comparable<T>> List<T> sort(List<T> unsorted) {
        return Arrays.asList(sort(unsorted.toArray((T[]) new Comparable[unsorted.size()])));
    }

}
