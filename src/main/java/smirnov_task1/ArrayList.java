package smirnov_task1;

import java.util.Arrays;

public class ArrayList implements List {

    private Object[] arrayList;
    private int size = 0;

    public ArrayList() {
        this(10);
    }

    public ArrayList(int i) {
        arrayList = new Object[i];
    }


    @Override
    public void add(Object value) {
        if (size == arrayList.length - 1) {
            resize();
        }
        arrayList[size++] = value;
    }

    @Override
    public void add(Object value, int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == arrayList.length - 1) {
            resize();
        }
        for (int i = ++size; i > index; i--) {
            arrayList[i] = arrayList[i - 1];
        }
        arrayList[index] = value;

    }

    @Override
    public Object remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Object o = get(index);
        for (int i = index; i < size; i++) {
            arrayList[i] = arrayList[i + 1];
        }
        arrayList[size] = null;
        size--;

        return o;
    }

    @Override
    public Object get(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }
        return arrayList[index];
    }

    @Override
    public Object set(Object value, int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return arrayList[index] = value;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            arrayList[i] = null;
        }
        size = 0;
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

    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (checkValue(get(i), value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--) {
            if (checkValue(get(i), value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(prefix);
            prefix = ", ";
            Object o = get(i);
            if (o == null) {
                sb.append("null");
            } else {
                sb.append(o);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private void resize() {
        Object[] newList = new Object[arrayList.length * 2];
        for (int i = 0; i < arrayList.length; i++) {
            newList[i] = arrayList[i];
        }
        arrayList = newList;
    }

    private boolean checkValue(Object value1, Object value2) {
        if (value1 == null) {
            return value2 == null;
        }
        return value1.equals(value2);
    }
}
