package ru.otus;

import com.google.gson.JsonObject;

public class Njson {
    Object o;
    String json;


    Njson(Object o){
        this.o=o;

    }
    public String toJson(){

        return json;
    }
}
