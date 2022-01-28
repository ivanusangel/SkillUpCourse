package org.ivan_smirnov.datastructure.map;

import org.ivan_smirnov.datastructure.list.ArrayList;
import org.ivan_smirnov.datastructure.list.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayMap<K, V> implements Map<K, V> {

    public static final int DEFAULT_BUCKET_NUM = 10;
    private int size = 0;
    private int bucketsNum;

    private List<Entry<K, V>>[] buckets;

    public ArrayMap() {
        this(DEFAULT_BUCKET_NUM);
    }

    public ArrayMap(int bucketNum) {
        this.bucketsNum = bucketNum;
        buckets = new ArrayList[bucketNum];
    }

    @Override
    public V put(K key, V value) {
        int currentBucket = getBucket(key);
        Entry<K, V> entry = null;
        if (buckets[currentBucket] == null) {
            buckets[currentBucket] = new ArrayList<>();
        } else {
            entry = getEntry(key);
        }
        if (entry == null) {
            buckets[currentBucket].add(new Entry<>(key, value));
            size++;
            return value;
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
        List<Entry<K, V>> bucketList = buckets[getBucket(key)];
        if (bucketList != null) {
            Iterator<Entry<K, V>> iterator = bucketList.iterator();
            while (iterator.hasNext()) {
                Entry<K, V> entry = iterator.next();
                if (Objects.equals(entry.key, key)) {
                    iterator.remove();
                    size--;
                    return entry;
                }
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
            Iterator<Entry<K, V>> current = null;
            boolean removed = true;

            @Override
            public boolean hasNext() {
                return currentElementNum < size;
            }

            @Override
            public Entry<K, V> next() {
                if (hasNext()) {
                    while (currentBucketNum < bucketsNum) {
                        List<Entry<K, V>> currentBucket = buckets[currentBucketNum];
                        if (currentBucket == null) {
                            currentBucketNum++;
                            continue;
                        }
                        if (current == null) {
                            current = currentBucket.iterator();
                        }
                        if (current.hasNext()) {
                            removed = false;
                            currentElementNum++;
                            return current.next();
                        } else {
                            currentBucketNum++;
                            current = null;
                        }
                    }
                }
                throw new NoSuchElementException("There is no more elements in the map");
            }

            @Override
            public void remove() {
                if (removed) {
                    throw new IllegalStateException("Nothing to remove. Remove() called without next()");
                }
                current.remove();
                removed = true;
            }
        };
    }

    private int getBucket(K key) {
        if (key == null) {
            return 0;
        }
        return key.hashCode() % bucketsNum;
    }

    private Entry<K, V> getEntry(K key) {
        List<Entry<K, V>> bucketList = buckets[getBucket(key)];
        if (bucketList == null) {
            return null;
        }
        for (Entry<K, V> entry : bucketList) {
            if (Objects.equals(entry.key, key)) {
                return entry;
            }
        }
        return null;
    }
}
