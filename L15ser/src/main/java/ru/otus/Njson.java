package ru.otus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Njson {

    String json;

    Set<String> INT_TYPES = new HashSet<String>(Arrays.asList("java.lang.Integer", "int"));
    Set<String> STRING_TYPES = new HashSet<String>(Arrays.asList("java.lang.String"));
    Set<String> BOOLEAN_TYPES = new HashSet<String>(Arrays.asList("boolean"));
    Set<String> ARRAY_TYPES = new HashSet<String>(Arrays.asList("java.util.List","[Ljava.lang.String"));


    public String toJson(Object o) {
        Class clazz = o.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        JsonObject jsonObject = new JsonObject();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            try {
                System.out.println("Field: " + f.get(o)+" ." + f.getType().getName() +". " + f.getName());
                String type = f.getType().getTypeName();
                if (INT_TYPES.contains(f.getType().getName())) {
                    jsonObject.addProperty(f.getName(), (int) f.get(o));
                    System.out.println("int " + f.get(o));
                } else if (STRING_TYPES.contains(f.getType().getName())) {
                    jsonObject.addProperty(f.getName(), (String) f.get(o));
                } else if (BOOLEAN_TYPES.contains(f.getType().getName())) {

                    jsonObject.addProperty(f.getName(), (Boolean) f.get(o));
                }
                else if(ARRAY_TYPES.contains(f.getType().getName()))

                {
                    jsonObject.add(f.getName(), new JsonElement() {
                        @Override
                        public JsonElement deepCopy() {
                            return null;
                        }
                    });

            }






            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println(jsonObject);
        return jsonObject.toString();
    }


}
