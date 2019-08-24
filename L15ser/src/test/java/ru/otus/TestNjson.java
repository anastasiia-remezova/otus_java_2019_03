package ru.otus;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestNjson {
    private static Njson n;
    private static Programmer Pit;
    private static String json;

    @BeforeAll
    public static void init() throws Exception {

    }

    @Test
    public void testToJson() throws Exception {
        Pit = new Programmer("Pit", 21, Arrays.asList("Java"));
        n = new Njson();
        json = n.toJson(Pit);
        System.out.println("Old Pit: " + n.toJson(Pit));
        Gson gson = new Gson();
        Programmer newPit = gson.fromJson(json, Programmer.class);
        assertTrue(newPit.equals(Pit));
    }

}


