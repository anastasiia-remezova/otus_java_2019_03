package ru.otus;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;


public class ProxyHandler {


    static TestLoginInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoginInterface) Proxy.newProxyInstance(ProxyHandler.class.getClassLoader(),
                new Class<?>[]{TestLoginInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoginInterface myClass;

        DemoInvocationHandler(TestLoginInterface myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method m = myClass.getClass().getMethod(method.getName(), method.getParameterTypes());


            Annotation[] declaredAnnotations = m.getDeclaredAnnotations();
            StringBuilder params = new StringBuilder();
            for(Annotation a:declaredAnnotations)
            {
                if(a.toString().equals("@ru.otus.Log()")){
                    m.setAccessible(true);
                    Parameter[] parameters = m.getParameters();
                    int i=0;
                    for (Parameter p:parameters)
                    {
                        params.append(p.getName() );
                        params.append(" ");
                        Integer value = (Integer) args[i];

                        params.append(value);
                        params.append(" ");
                        i++;
                    }
                    System.out.println("result: " + method.getName() + " " + params);
                }
            }

            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }

}
