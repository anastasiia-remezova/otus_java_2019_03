package ru.otus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNjson {
    private Njson n;
    private Programmer Pit;
    private String json;

    @BeforeAll
    public void init()
    {
        Pit =  new Programmer("Pit", 21, (String[]) Arrays.asList("english").toArray(), Arrays.asList("Java"));
        n = new Njson(Pit);
        json = n.toJson();

    }

    @Test
    public void testToJson()

    {
        Gson gson = new Gson();
        Programmer newPit = gson.fromJson(json, Programmer.class);
        assertEquals (newPit,Pit);
    }

}


