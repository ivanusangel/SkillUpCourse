package org.ivan_smirnov.datastructure.list;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList extends AbstractList implements List, Iterable {

    private static final int INITIAL_SIZE = 10;
    private static final float RESIZE_FACTOR = 1.5f;

    private Object[] arrayList;

    public ArrayList() {
        this(INITIAL_SIZE);
    }

    public ArrayList(int i) {
        arrayList = new Object[i];
    }

    @Override
    public void add(Object value, int index) {
        checkIndex(index, size);
        if (size == arrayList.length) {
            resize();
        }
        System.arraycopy(arrayList, index, arrayList, index + 1, size - index);
        arrayList[index] = value;
        size++;
    }

    @Override
    public Object remove(int index) {
        checkIndex(index, size - 1);
        Object o = get(index);
        if (index < size - 1) {
            System.arraycopy(arrayList, index + 1, arrayList, index, size - index);
        } else {
            arrayList[size - 1] = null;
        }
        size--;
        return o;
    }

    @Override
    public Object get(int index) {
        checkIndex(index, size - 1);
        return arrayList[index];
    }

    @Override
    public Object set(Object value, int index) {
        checkIndex(index, size - 1);
        Object o = get(index);
        arrayList[index] = value;
        return o;
    }

    @Override
    public void clear() {
        arrayList = new Object[arrayList.length];
        size = 0;
    }

    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(get(i), value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(get(i), value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Object next() {
                return get(index++);
            }

            @Override
            public void remove() {
                ArrayList.this.remove(index);
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            stringJoiner.add(String.valueOf(get(i)));
        }
        return stringJoiner.toString();
    }

    private void resize() {
        Object[] newList = new Object[(int) (arrayList.length * RESIZE_FACTOR)];
        System.arraycopy(arrayList, 0, newList, 0, size);
        arrayList = newList;
    }
}
