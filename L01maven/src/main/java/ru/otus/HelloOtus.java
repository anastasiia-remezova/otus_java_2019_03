package ru.otus;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collections;


public class HelloOtus {
    public static void main(String[] args) {
        Multimap<String, Integer> scores = HashMultimap.create();
        scores.put("Bob", 20);
        scores.put("Bob", 10);
        scores.put("Bob", 15);
        System.out.println(Collections.max(scores.get("Bob")));
    }
}
