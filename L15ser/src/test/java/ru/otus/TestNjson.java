package ru.otus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNjson {
    private static Njson n;
    private static Programmer Pit;
    private static String json;

    @BeforeAll
    public static void init() {
        String[] lang = new String[2];
        lang[0] = "Russian";
        lang[1] = "English";
        Pit = new Programmer("Pit", 21,
               lang,
                Arrays.asList("Java"));
        n = new Njson();
        json = n.toJson(Pit);
        System.out.println(json);
    }

    @Test
    public void testToJson() {
        Gson gson = new Gson();
        Programmer newPit = gson.fromJson(json, Programmer.class);
        assertEquals(newPit, Pit);
    }

}


