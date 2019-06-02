package ru.otus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class TestValidation {
    private static Map<String, Integer> priors = new HashMap<String, Integer>();

    public TestValidation() {
        priors.put("Before", 0);
        priors.put("Test", 1);
        priors.put("After", 2);
    }

    public static void run(Class c) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = c;
        Constructor constructor = c.getDeclaredConstructor();
        Map<Method, Integer> methodsPriors = new HashMap<>();

        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            String annotationName = null;

            //System.out.println("We found method:" + method);
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {

                annotationName = annotation.toString().substring(annotation.toString().lastIndexOf(".") + 1).replace("(", "").replace(")", "");
                try {
                    methodsPriors.put(method, priors.get(annotationName));
                    //System.out.println("Ann: " + annotationName + " " + priors.get(annotationName));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        final HashMap<Method, Integer> sortedMethods = methodsPriors.entrySet()
            .stream()
            .sorted(comparingByValue())
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                LinkedHashMap::new));

        for (Map.Entry<Method, Integer> entry : sortedMethods.entrySet()) {
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            TestExample o1 = (TestExample) constructor.newInstance();
            entry.getKey().setAccessible(true);
            Object o = entry.getKey().invoke(o1);
        }
    }
}
