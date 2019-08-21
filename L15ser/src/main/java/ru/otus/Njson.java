package ru.otus;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.*;

public class Njson {

    String json;

    Set<String> INT_TYPES = new HashSet<String>(Arrays.asList("java.lang.Integer", "int"));
    Set<String> STRING_TYPES = new HashSet<String>(Arrays.asList("java.lang.String"));
    Set<String> BOOLEAN_TYPES = new HashSet<String>(Arrays.asList("boolean"));
    Set<String> ARRAY_TYPES = new HashSet<String>(Arrays.asList("java.util.List"));

    public String toJson(Object o) throws Exception {
        Class clazz = o.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();

        JSONObject jsonObject = new JSONObject();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            try {
                String type = f.getType().getTypeName();
                if (INT_TYPES.contains(f.getType().getName())) {
                    jsonObject.put(f.getName(), (int) f.get(o));
                } else if (STRING_TYPES.contains(f.getType().getName())) {
                    jsonObject.put(f.getName(), (String) f.get(o));
                } else if (BOOLEAN_TYPES.contains(f.getType().getName())) {
                    jsonObject.put(f.getName(), (Boolean) f.get(o));
                } else if (ARRAY_TYPES.contains(f.getType().getName())) {
                    JSONArray jsArray = new JSONArray();
                    Collection collection = (Collection) f.get(o);
                    collection.forEach(c -> jsArray.add(c));
                    jsonObject.put(f.getName(), jsArray);
                }
            } catch (IllegalAccessException e) {
                throw new Exception(e);
            }
        }
        return jsonObject.toString();
    }
}
