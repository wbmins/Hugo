package Sort.Bubble;

import Sort.Sort;
import org.junit.Test;

import java.util.Arrays;

import static Sort.Util.*;

public class BubbleSort implements Sort {

    /**
     * @param unsorted 一个可以排序的数组
     * @return 一个排好序列的数组
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        //一趟冒泡是否交换的标志
        boolean change = true;
        for (int i = unsorted.length - 1; i > 1 && change; i--) {
            change = false;
            for (int j = 1; j < i; j++) {
                if (more(unsorted[j], unsorted[j + 1])) {
                    swap(unsorted, j + 1, j);
                    change = true;
                }
            }
        }
        return unsorted;
    }

    @Test
    public void test(){
        Integer[] unsorted = {0, 4, 23, 6, 78, 1, 54, 231, 9, 12};
        BubbleSort b = new BubbleSort();
        b.sort(unsorted);
        System.out.println(Arrays.toString(unsorted));
    }

}
