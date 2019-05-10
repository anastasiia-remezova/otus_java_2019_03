package ru.otus;

import ru.otus.anotation.Before;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestValidation {
    public static void run(Class c) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = c;
        Constructor constructor = c.getDeclaredConstructor();



//        Class<LengthValidator> validatorClass = LengthValidator.class;
//        Constructor<LengthValidator> constructor = validatorClass.getDeclaredConstructor(String.class);
//        LengthValidator lengthValidator = constructor.newInstance("Hello, world!");
//
//        lengthValidator.validate(person);


        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method: declaredMethods)
        {
            TestExample o1 = (TestExample) constructor.newInstance();
            

            System.out.println("We found method:" + method);
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for(Annotation annotation : declaredAnnotations)
            {
                System.out.println("With such annotation:" + annotation);
            }
        }

    }
}
