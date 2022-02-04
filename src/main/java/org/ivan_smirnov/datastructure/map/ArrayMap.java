package org.ivan_smirnov.datastructure.map;

import org.ivan_smirnov.datastructure.list.ArrayList;
import org.ivan_smirnov.datastructure.list.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayMap<K, V> implements Map<K, V> {

    public static final int DEFAULT_BUCKET_NUM = 10;
    public static final float LOAD_FACTOR = 0.75f;
    private int size = 0;

    private List<Entry<K, V>>[] buckets;

    public ArrayMap() {
        this(DEFAULT_BUCKET_NUM);
    }

    public ArrayMap(int bucketNum) {
        buckets = new ArrayList[bucketNum];
        for (int i = 0; i < bucketNum; i++) {
            buckets[i] = new ArrayList<>();
        }
    }

    @Override
    public V put(K key, V value) {
        int currentBucketIndex = getBucketIndex(key);
        Entry<K, V> entry = getEntry(key, currentBucketIndex);
        if (entry == null) {
            if (!buckets[currentBucketIndex].isEmpty() && isOverloaded()) {
                resize();
                currentBucketIndex = getBucketIndex(key);
            }
            buckets[currentBucketIndex].add(new Entry<>(key, value));
            size++;
            return null;
        } else {
            V oldValue = entry.value;
            entry.value = value;
            return oldValue;
        }
    }


    @Override
    public V get(K key) {
        Entry<K, V> entry = getEntry(key);
        if (entry == null) {
            throw new NoSuchElementException("There is no key = " + key);
        }
        return entry.value;
    }

    @Override
    public boolean containsKey(K key) {
        return getEntry(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Entry<K, V> remove(K key) {
        List<Entry<K, V>> bucket = buckets[getBucketIndex(key)];
        Iterator<Entry<K, V>> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (Objects.equals(entry.key, key)) {
                iterator.remove();
                size--;
                return entry;
            }
        }
        return null;
    }

    @Override
    public List<K> keySet() {
        List<K> keyList = new ArrayList<>(size);
        for (Entry<K, V> entry : this) {
            keyList.add(entry.key);
        }
        return keyList;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<>() {
            int currentBucketNum = 0;
            int currentElementNum = 0;
            Iterator<Entry<K, V>> currentIterator = null;
            boolean nextCalled = false;

            @Override
            public boolean hasNext() {
                return currentElementNum < size;
            }

            @Override
            public Entry<K, V> next() {
                if (hasNext()) {
                    while (currentBucketNum < buckets.length) {
                        List<Entry<K, V>> currentBucket = buckets[currentBucketNum];
                        if (currentIterator == null) {
                            currentIterator = currentBucket.iterator();
                        }
                        if (currentIterator.hasNext()) {
                            nextCalled = true;
                            currentElementNum++;
                            return currentIterator.next();
                        } else {
                            currentBucketNum++;
                            currentIterator = null;
                        }
                    }
                }
                throw new NoSuchElementException("There is no more elements in the map");
            }

            @Override
            public void remove() {
                if (!nextCalled) {
                    throw new IllegalStateException("Nothing to remove. Remove() called without next()");
                }
                currentIterator.remove();
                size--;
                nextCalled = false;
            }
        };
    }

    private int getBucketIndex(K key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % buckets.length;
    }

    private Entry<K, V> getEntry(K key) {
        return getEntry(key, getBucketIndex(key));
    }

    private Entry<K, V> getEntry(K key, int index) {
        List<Entry<K, V>> bucket = buckets[index];
        if (bucket != null) {
            for (Entry<K, V> entry : bucket) {
                if (Objects.equals(entry.key, key)) {
                    return entry;
                }
            }
        }
        return null;
    }

    private boolean isOverloaded() {
        return buckets.length * LOAD_FACTOR <= size;
    }

    private void resize() {
        ArrayMap<K, V> newMap = new ArrayMap<>(buckets.length << 1);
        for (Entry<K, V> entry : this) {
            newMap.put(entry.key, entry.value);
        }
        buckets = newMap.buckets;
    }
}
