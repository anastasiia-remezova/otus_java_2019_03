package ru.otus;

import ru.otus.anotation.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TestValidation {
    private Map<String, Integer> methodPriors = 

    public static void run(Class c) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = c;
        Constructor constructor = c.getDeclaredConstructor();
        Map<Method, String> methodsPriors = new HashMap<Method,String>();

        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method: declaredMethods)
        {
            TestExample o1 = (TestExample) constructor.newInstance();

            method.setAccessible(true);
            Object o = method.invoke(o1);
            System.out.println("We found method:" + method);
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for(Annotation annotation : declaredAnnotations)
            {
                System.out.println("With such annotation:" + annotation);
            }
        }




    }


}
