package org.ivan_smirnov.datastructure.map;

import org.ivan_smirnov.datastructure.list.List;

import java.util.Iterator;

public interface Map<K, V> extends Iterable<Map.Entry<K, V>> {

    V put(K key, V value);

    V get(K key);

    boolean containsKey(K key);

    int size();

    Entry<K, V> remove(K key);

    List<K> keySet();

    Iterator<Entry<K, V>> iterator();

    class Entry<K, V> {
        K key;
        V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}