package fleek.code.utils;

import java.util.LinkedList;

public class ObjectList<V> extends LinkedList<V> {
    public static <V> ObjectList<V> of(V... values) {
        final ObjectList<V> list = new ObjectList<>();

        for (V value : values) list.add(value);

        return list;
    }
}
