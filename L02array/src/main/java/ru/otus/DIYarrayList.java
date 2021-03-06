package ru.otus;

import java.util.*;

class DIYarrayList<T> implements List<T> {
    private final int INIT_SIZE = 10;
    private int size = 0;
    private final static Object[] defaultArray = {};
    private Object[] mainArray;
    private final int DEFAULT_DELTA = 10;

    public void sort(Comparator<? super T> c) {
        Arrays.sort(mainArray, 0, size, (Comparator<? super Object>) c);
    }

    public DIYarrayList() {
        this.mainArray = defaultArray;
    }

    public void copy(DIYarrayList<? super T> dest) {
        dest.addAll(this);
    }

    public boolean addAll(Collection<? extends T> c) {

        Object[] newArray = c.toArray();
        int newArraySize = newArray.length;

        Object[] tempArray = new Object[size];
        System.arraycopy(mainArray, 0, tempArray, 0, size);


        if (size + newArraySize > mainArray.length) {
            mainArray = new Object[size + newArraySize];
            System.arraycopy(tempArray, 0, mainArray, 0, tempArray.length);
        }

        System.arraycopy(newArray, 0, mainArray, size, newArraySize);

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

        if (mainArray.length <= size) {
            Object[] newArray = new Object[size];
            newArray = mainArray;
            mainArray = new Object[size + DEFAULT_DELTA];
            System.arraycopy(newArray, 0, mainArray, 0, size);
        }
        mainArray[size] = t;
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
        return (T) mainArray[index];

    }

    public T set(int index, T element) {
        this.mainArray[index] = element;
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

        return new ListIterator<T>() {
            private int cursor = 0;

            public boolean hasNext() {
                throw new UnsupportedOperationException();
            }

            public T next() {
                cursor++;
                return (T) mainArray[cursor - 1];
            }

            public boolean hasPrevious() {
                throw new UnsupportedOperationException();
            }

            public T previous() {
                throw new UnsupportedOperationException();
            }

            public int nextIndex() {
                throw new UnsupportedOperationException();
            }

            public int previousIndex() {
                throw new UnsupportedOperationException();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public void set(T t) {
                mainArray[cursor] = t;
            }

            public void add(T t) {
                throw new UnsupportedOperationException();
            }
        };
    }

    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }


}