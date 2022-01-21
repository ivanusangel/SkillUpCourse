package org.ivan_smirnov.datastructure.list;

public class LinkedList implements List {

    private class Node {
        Node prev;
        Node next;
        Object value;

        public Node(Object value) {
            this.value = value;
        }
    }

    private Node first;
    private Node last;
    private int size = 0;

    public LinkedList() {
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
        Node newNode = new Node(value);
        if (index == 0) {
            if (first != null) {
                first.prev = newNode;
            }
            newNode.next = first;
            first = newNode;
        }
        if (index == size) {
            if (last != null) {
                last.next = newNode;
            }
            newNode.prev = last;
            last = newNode;
        }
        if (index > 0 && index < size) {
            Node currentNode = getNode(index);
            currentNode.prev.next = newNode;
            newNode.next = currentNode;
            newNode.prev = currentNode.prev;
            currentNode.prev = newNode;
        }
        size++;
    }

    @Override
    public Object remove(int index) {
        Node currentNode = getNode(index);
        currentNode.prev.next = currentNode.next;
        currentNode.next.prev = currentNode.prev;
        return currentNode.value;
    }

    @Override
    public Object get(int index) {
        return getNode(index).value;
    }

    @Override
    public Object set(Object value, int index) {
        return null;
    }

    @Override
    public void clear() { //can be memory leak? Should I clean all elements?
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object value) {
        return false;
    }

    @Override
    public int indexOf(Object value) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object value) {
        return 0;
    }

    private Node getNode(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Index: " + index + ". Expected between 0 and " + (size - 1));
        }
        Node currentNode;
        if (index * 2 <= size) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size; i > index + 1; i--) {
                currentNode = currentNode.prev;
            }
        }
        return currentNode;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
