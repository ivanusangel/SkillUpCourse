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
        int currentBucketIndex = getBucketIndex(key);
        Entry<K, V> entry = null;
        if (buckets[currentBucketIndex] == null) {
            buckets[currentBucketIndex] = new ArrayList<>();
        } else {
            entry = getEntry(key);
        }
        if (entry == null) {
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
        if (bucket != null) {
            Iterator<Entry<K, V>> iterator = bucket.iterator();
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
            Iterator<Entry<K, V>> currentIterator = null;
            boolean nextCalled = false;

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
        return Math.abs(key.hashCode()) % bucketsNum;
    }

    private Entry<K, V> getEntry(K key) {
        List<Entry<K, V>> bucketList = buckets[getBucketIndex(key)];
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
