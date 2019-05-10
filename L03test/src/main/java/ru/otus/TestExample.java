package ru.otus;

import ru.otus.anotation.After;
import ru.otus.anotation.Before;
import ru.otus.anotation.Test;

public class TestExample {

    @Before
    public static void before()
    {
        System.out.println("We are exec before");
    }

    @Test
    public static void test()
    {
        System.out.println("We are exec test");
    }

    @After
    public static void after()
    {
        System.out.println("We are exec after");
    }

    @Before
    public static void before2()
    {
        System.out.println("We are exec before 2");
    }


}
