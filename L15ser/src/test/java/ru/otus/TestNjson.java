package ru.otus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestNjson {
    private static Njson n;
    private static Programmer Pit;
    private static String json;

    @BeforeAll
    public static void init() {
        Pit = new Programmer("Pit", 21, Arrays.asList("Java"));
        n = new Njson();
        json = n.toJson(Pit);
        System.out.println("Old Pit: " + n.toJson(Pit));
    }

    @Test
    public void testToJson() {
        Gson gson = new Gson();
        Programmer newPit = gson.fromJson(json, Programmer.class);
        System.out.println("New Pit: " + n.toJson(newPit));
        assertTrue(newPit.equals(Pit));
    }

}


