package ru.otus;

class Demo {

    public static void main(String[] args)
    {
        Demo d = new Demo();
        d.action();
    }

    public void action() {
        new TestLogging().calculation(6); }
}