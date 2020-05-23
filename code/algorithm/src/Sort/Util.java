package Sort;

public class Util {

    public static <T> boolean swap(T[] sort, int x, int y) {
        T t = sort[x];
        sort[x] = sort[y];
        sort[y] = t;
        return true;
    }

    public static <T extends Comparable<T>> boolean less(T x, T y) {
        return x.compareTo(y) < 0;
    }

    public static <T extends Comparable<T>> boolean more(T x, T y) {
        return x.compareTo(y) > 0;
    }

    public static <T extends Comparable<T>> boolean equal(T x, T y) {
        return x.compareTo(y) == 0;
    }


    }
