package ru.otus;

import java.util.*;

class DIYarrayList<T> implements List<T> {
    private final int INIT_SIZE = 10;
    private int size = 0;
    private final static Object[] defaultArray = {};
    private Object[] ourArray;
    private final int DEFAULT_DELTA = 10;

    public void sort(Comparator<? super T> c) {
        Arrays.sort(ourArray, 0, size, (Comparator<? super Object>) c);
    }

    public DIYarrayList() {
        this.ourArray = defaultArray;
    }

    public void copy(DIYarrayList<? super T> dest) {
        dest.addAll(this);
    }

    public boolean addAll(Collection<? extends T> c) {

        Object[] newArray = c.toArray();
        int newArraySize = newArray.length;
        Object[] ourNewArray = new Object[size + newArraySize];
        System.arraycopy(ourArray, 0, ourNewArray, 0, ourArray.length);
        System.arraycopy(newArray, 0, ourNewArray, size, newArraySize);
        ourArray = new Object[size + newArraySize];
        System.arraycopy(ourNewArray, 0, ourArray, 0, ourNewArray.length);
        this.size = size + newArraySize;
        return c.size() != 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean add(T t) {

        if (ourArray.length==size)
        {
            Object[] newArray = new Object[size];
            newArray = ourArray;
            ourArray = new Object[size + DEFAULT_DELTA];
            System.arraycopy(ourArray, 0, newArray, 0, size);
        }
        ourArray[size+1] = t;
        size++;
        return true;
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }


    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {

    }

    public T get(int index) {
        return (T) ourArray[index];

    }

    public T set(int index, T element) {
        this.ourArray[index] = element;
        return element;
    }

    public void add(int index, T element) {

    }

    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
        //return new DIYListIterator();
    }

    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }




}