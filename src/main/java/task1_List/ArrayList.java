package task1_List;

import java.util.Objects;

public class ArrayList implements List {

    public static final int INITIAL_SIZE = 10;
    public static final float RESIZE_FACTOR = 1.5f;

    private Object[] arrayList;
    private int size = 0;

    public ArrayList() {
        this(INITIAL_SIZE);
    }

    public ArrayList(int i) {
        arrayList = new Object[i];
    }


    @Override
    public void add(Object value) {
        add(value, size);
    }

    @Override
    public void add(Object value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ". Expected between 0 and " + size);
        }
        if (size == arrayList.length) {
            resize();
        }
        System.arraycopy(arrayList, index, arrayList, index + 1, size - index);
        arrayList[index] = value;
        size++;
    }

    @Override
    public Object remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index: " + index + ". Expected between 0 and " + (size - 1));
        }
        Object o = get(index);
        System.arraycopy(arrayList, index + 1, arrayList, index, size - index);
        arrayList[size] = null;
        size--;

        return o;
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index: " + index + ". Expected between 0 and " + (size - 1));
        }
        return arrayList[index];
    }

    @Override
    public Object set(Object value, int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index: " + index + ". Expected between 0 and " + (size - 1));
        }
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(prefix);
            prefix = ", ";
            Object o = get(i);
            sb.append(o);
        }
        sb.append("]");
        return sb.toString();
    }

    private void resize() {
        Object[] newList = new Object[(int) (arrayList.length * RESIZE_FACTOR)];
        System.arraycopy(arrayList, 0, newList, 0, size);
        arrayList = newList;
    }
}
