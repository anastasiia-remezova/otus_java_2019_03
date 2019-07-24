package ru.otus;

import com.google.gson.Gson;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonWriter;

public class Demo {




    public static void main(String[] arg)
    {

    JsonObject jsonCreated = Json.createObjectBuilder()
        .add("firstName", "Duke")
        .add("age", 28)
        .add("streetAddress", "100 Internet Dr")
        .add("phoneNumbers", Json.createArrayBuilder()
            .add(Json.createObjectBuilder()
                .add("type", "home")
                .add("number", "222-222-2222")))
        .build();
        System.out.println("jsonCreated:" + jsonCreated);

        Gson gson = new Gson();
//        BagOfPrimitives obj = new BagOfPrimitives(22, "test", 10);
//        System.out.println(obj);
//
//        String json = gson.toJson(obj);
//        System.out.println(json);
//
//        BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);
    }
}
