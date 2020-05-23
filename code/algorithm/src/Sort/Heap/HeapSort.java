package Sort.Heap;

import Sort.Sort;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static Sort.Util.*;

public class HeapSort implements Sort {

    private static class Heap<T extends Comparable<T>> {

        private T[] heap;

        public Heap(T[] heap) {
            this.heap = heap;
        }

        /**
         * 一次堆调整
         * @param position 待调整元素位置
         * @param length   待调整数组长度
         */
        private void AdjustDown(int position, int length) {
            T t = heap[0];
            heap[0] = heap[position];
            for (int i = 2*position; i <= length; i *= 2) {
                if(i < length && less(heap[i],heap[i + 1])) i++;
                if(more(heap[0],heap[i]) || equal(heap[0],heap[i])) break;
                heap[position] = heap[i];
                position = i;
            }
            heap[position] = heap[0];
            heap[0] = t;
        }

        /**
         * 建大根堆
         */
        private void BuildMaxHeap() {
            for (int i = (heap.length - 1)/2; i > 0; i--)
                AdjustDown(i,heap.length);
        }

    }
    /**
     * @param unsorted 一个可以排序的数组
     * @return 一个排好序列的数组
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        return sort(Arrays.asList(unsorted)).toArray(unsorted);
    }

    @Override
    public <T extends Comparable<T>> List<T> sort(List<T> unsorted) {
        int size = unsorted.size() - 1;
        Heap<T> heap = new Heap<>(unsorted.toArray((T[]) new Comparable[unsorted.size()]));
        //建大根堆
        heap.BuildMaxHeap();
        for (int i = size; i > 1; i--) {
            //调整堆顶元素，数组长度减一
            heap.AdjustDown(1,i - 1);
        }
        return Arrays.asList(heap.heap);
    }

    @Test
    public void test(){
        //默认不启用 0 号位置元素
        Integer[] heap = {0, 4, 23, 6, 78, 1, 54, 231, 9, 12};
        HeapSort heapSort = new HeapSort();
        System.out.println(Arrays.toString(heapSort.sort(heap)));
    }
}
