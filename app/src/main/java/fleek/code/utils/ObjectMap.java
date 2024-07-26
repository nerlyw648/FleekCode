package fleek.code.utils;

import java.util.LinkedHashMap;

public class ObjectMap<K, V> extends LinkedHashMap<K, V> {
    public static <K, V> ObjectMap<K, V> of(Object... entries) {
        if (entries.length % 2 != 0) throw new IllegalArgumentException("Invalid number of arguments. Arguments must be in pairs (key, value).");

        final ObjectMap<K, V> map = new ObjectMap<>();

        for (int i = 0; i < entries.length; i += 2) {
            final K key = (K) entries[i];
            final V value = (V) entries[i + 1];

            if (key == null) throw new NullPointerException("Key must not be null");

            map.put(key, value);
        }

        return map;
    }
}
