package ru.otus;

import java.util.*;


public class TestDIYarrayList {

    public static void main(String[] args) {
        //First
        List<Integer> my = new ru.otus.DIYarrayList<Integer>();
        List<Integer> dest = new DIYarrayList<Integer>();


        //first
        my.addAll(Arrays.asList(8, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21));
        my.add(101);
        Collections.addAll(my, 58, 65, 233, 33, 333);
        my.addAll(Arrays.asList(56, 65, 233, 33));
        for (int i = 0; i < my.size(); i++) {
            System.out.print(my.get(i) + " ");
        }

        //Second
        List<Integer> src = new DIYarrayList<Integer>();
        src.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));

        List<Integer> destCopy = new ArrayList<Integer>();
        destCopy.addAll(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        Collections.copy(destCopy, src);

        System.out.println("\nCopy: " + destCopy.size());
        for (int i = 0; i < destCopy.size(); i++) {
            System.out.print(destCopy.get(i) + " ");
        }


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
}
