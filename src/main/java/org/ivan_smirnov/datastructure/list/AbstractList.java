package org.ivan_smirnov.datastructure.list;

public abstract class AbstractList implements List {
    protected int size = 0;

    @Override
    public void add(Object value) {
        add(value, size);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    protected void checkIndex(int index, int max) {
        if (index < 0 || index > max) {

            throw new IndexOutOfBoundsException("Index: " + index + ". Expected between 0 and " + (size - 1));
        }
    }
}
