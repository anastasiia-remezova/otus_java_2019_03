package ru.otus;

class Demo {

    public static void main(String[] args)
    {
        Demo d = new Demo();
        d.action();
    }

    public void action() {
        TestLoginInterface t =  ProxyHandler.createMyClass();
        t.calculation(6);
        t.calculation(6,4);
        t.plus(6);

    }
}