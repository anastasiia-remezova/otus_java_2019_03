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




                    System.out.println("We in if"  + parameters.length);
                    for (Parameter p:parameters)
                    {
                        //System.out.println(parameterType.getName());

                        params.append("param name: " + p.getName() );
                        params.append(" ");
                        params.append("param value:" + p);

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


//    public aspect ValidationAspect {
//
//        pointcut serviceMethodCall() : execution(public * com.yourcompany.**.*(..));
//
//        Object around(final Object[] args) : serviceMethodCall() && args(args){
//        Signature signature = thisJoinPointStaticPart.getSignature();
//        if(signature instanceof MethodSignature){
//        MethodSignature ms = (MethodSignature) signature;
//        Method method = ms.getMethod();
//        Annotation[][] parameterAnnotations =
//        method.getParameterAnnotations();
//        String[] parameterNames = ms.getParameterNames();
//        for(int i = 0; i < parameterAnnotations.length; i++){
//        Annotation[] annotations = parameterAnnotations[i];
//        validateParameter(parameterNames[i], args[i],annotations);
//        }
//        }
//        return proceed(args);
//        }
//
//private void validateParameter(String paramName, Object object,
//        Annotation[] annotations){
//
//        // validate object against the annotations
//        // throw a RuntimeException if validation fails
//        }
//
//        }

//
//static class DemoInvocationHandler implements InvocationHandler {
//    private final MyClassInterface myClass;
//
//    DemoInvocationHandler(MyClassInterface myClass) {
//        this.myClass = myClass;
//    }
//
//    @Override
//    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        Method m = myClass.getClass().getMethod(method.getName(), method.getParameterTypes());
//        System.out.println(Arrays.toString(m.getDeclaredAnnotations()));
//
//        System.out.println("invoking method:" + method);
//        return method.invoke(myClass, args);
//    }
//
//    @Override
//    public String toString() {
//        return "DemoInvocationHandler{" +
//                "myClass=" + myClass +
//                '}';
//    }
//}

//package de.scrum_master.so;
//
//        import java.lang.reflect.InvocationHandler;
//        import java.lang.reflect.Method;
//        import java.lang.reflect.Proxy;
//
//public class Client {
//    public static void main(String[] args) {
//        final UserDaoImpl dao = new UserDaoImpl();
//        Method[] methods = dao.getClass().getMethods();
//        for (Method method : methods) {
//            if (method.getAnnotation(MyAnnotation.class) != null) {
//                System.out.println(method + " -> " + method.getDeclaringClass());
//                System.out.println("impl method is enhanced");
//            }
//        }
//        System.out.println("-----------");
//
//        UserDao proxy = (UserDao) Proxy.newProxyInstance(
//                dao.getClass().getClassLoader(),
//                dao.getClass().getInterfaces(),
//                new MyInvocationHandler(dao)
//        );
//
//        proxy.save();
//        System.out.println("-----------");
//        proxy.update();
//    }
//
//    private static class MyInvocationHandler implements InvocationHandler {
//        private Object realObject;
//
//        public MyInvocationHandler(Object realObject) {
//            this.realObject = realObject;
//        }
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            System.out.println(method + " -> " + method.getDeclaringClass());
//            if (method.getAnnotation(MyAnnotation.class) != null)
//                System.out.println("proxy method is enhanced");
//            if (realObject.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(MyAnnotation.class) != null)
//                System.out.println("real object method is enhanced");
//            return method.invoke(realObject, args);
//        }
//
//    }
//}