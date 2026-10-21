package helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Map<K, V> {
    HashMap<K, V> map1 = new HashMap<>();
    HashMap<V, K> map2 = new HashMap<>();

    public void put(K arg1, V arg2) {
        map1.put(arg1, arg2);
        map2.put(arg2, arg1);
    }
    public K getValue(V arg1) {
        return map2.get(arg1);
    }
    public V getKey(K arg1) {
        return map1.get(arg1);
    }

    public HashSet<V> getValueSet() {
        return new HashSet<>(map1.values());
    }
    public HashSet<K> getKeySet() {
        return new HashSet<>(map2.values());
    }
    public ArrayList<V> getValueList() {
        return new ArrayList<>(map1.values());
    }
    public ArrayList<K> getKeyList() {
        return new ArrayList<>(map2.values());
    }
}
