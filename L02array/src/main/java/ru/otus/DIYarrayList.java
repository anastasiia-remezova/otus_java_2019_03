package ru.otus;

import java.util.*;

class DIYarrayList<T> implements List<T> {
    private final int INIT_SIZE = 10;
    private int size = 0;
    Object[] defaultArray = {};
    Object[] ourArray;

    public static void main(String[] args) {
        //First
        List<Integer> my = new DIYarrayList<Integer>();
        List<Integer> dest = new DIYarrayList<Integer>();


        //first
        my.addAll(Arrays.asList(8, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22));
        my.add(101);
        Collections.addAll(my, 58, 65, 233, 33);

        my.addAll(Arrays.asList(56, 65, 233, 33));
        for (int i = 0; i < my.size(); i++) {
            System.out.println(my.get(i) + " ");
        }

        //Second
        List<Integer> my1 = new ArrayList<Integer>();
        my1.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22));

        List<Integer> destArray = new ArrayList<Integer>();
        Collections.copy(destArray, my);

        System.out.println("\ncopy ArrayList:");
        System.out.println(destArray.size());
        for (int i = 0; i < destArray.size(); i++) {
            System.out.println(destArray.get(i) + " ");
        }
        System.out.println("\ncopy DIYarrayList:");
        System.out.println(destArray.size());
        //Collections.copy(dest, my);



        //Third
        Collections.sort(my, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("\nSorted:");
        for (int i = 0; i < my.size(); i++) {
            System.out.print(my.get(i) + " ");
        }

    }

    public void sort(Comparator<? super T> c) {
        Arrays.sort(ourArray, 0, size, (Comparator<? super Object>) c);
    }

    public DIYarrayList() {
        this.ourArray = defaultArray;
    }

    public void copy(DIYarrayList<? super T> dest) {
//        System.out.println("sizes:" + dest.size() + this.size);
//        dest.size = this.size;
//        dest.ourArray = new Object[this.size];
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
        Object[] newArray = new Object[size + 1];
        System.arraycopy(ourArray, 0, newArray, 0, size);
        newArray[size] = t;
        ourArray = new Object[size + 1];
        System.arraycopy(newArray, 0, ourArray, 0, newArray.length);
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
    }

    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
