package org.ivan_smirnov.datastructure.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList<T> extends AbstractList<T> {

    private static final int INITIAL_SIZE = 10;
    private static final float RESIZE_FACTOR = 1.5f;

    private T[] arrayList;

    public ArrayList() {
        this(INITIAL_SIZE);
    }

    public ArrayList(int i) {
        arrayList = (T[]) new Object[i];
    }

    @Override
    public void add(T value, int index) {
        checkIndex(index, size);
        if (size == arrayList.length) {
            resize();
        }
        System.arraycopy(arrayList, index, arrayList, index + 1, size - index);
        arrayList[index] = value;
        size++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index, size - 1);
        T o = get(index);
        if (index < size - 1) {
            System.arraycopy(arrayList, index + 1, arrayList, index, size - index);
        } else {
            arrayList[size - 1] = null;
        }
        size--;
        return o;
    }

    @Override
    public T get(int index) {
        checkIndex(index, size - 1);
        return arrayList[index];
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index, size - 1);
        T o = get(index);
        arrayList[index] = value;
        return o;
    }

    @Override
    public void clear() {
        arrayList = (T[]) new Object[arrayList.length];
        size = 0;
    }

    @Override
    public int indexOf(T value) {
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
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int index = 0;
            boolean removed = true;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if(hasNext()) {
                    removed = false;
                    return get(index++);
                }
                throw new NoSuchElementException("There is no more elements in the list");
            }

            @Override
            public void remove() {
                if (removed) {
                    throw new IllegalStateException("Nothing to remove. Remove() called without next()");
                }
                ArrayList.this.remove(index - 1);
                size--;
                removed = true;
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
        T[] newList = (T[]) new Object[(int) (arrayList.length * RESIZE_FACTOR)];
        System.arraycopy(arrayList, 0, newList, 0, size);
        arrayList = newList;
    }
}
