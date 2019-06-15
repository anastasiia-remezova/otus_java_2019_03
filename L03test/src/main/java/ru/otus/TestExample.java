package ru.otus;

import ru.otus.anotation.After;
import ru.otus.anotation.Before;
import ru.otus.anotation.Test;

public class TestExample extends TestAsserts {


    public static Person stive = new Person("Stive");

    @Before
    public static void before()
    {

        //Person stive = new Person("Stive");
        stive.setAge(18);
        stive.setJob("student");
        System.out.println("We are exec before");
    }

    @Test
    public static void test()
    {
        Person superStive =  new Person("Stive");
        superStive.setAge(18);
        superStive.setJob("student");
        assertEquals(stive,superStive);
        System.out.println("We are exec test:" + assertEquals(stive,superStive));
    }

    @Test
    public static void test2()
    {
        Person superStive =  new Person("Stive");
        superStive.setAge(188);
        superStive.setJob("student");
        assertEquals(stive,superStive);
        System.out.println("We are exec test:" + assertEquals(stive,superStive));
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
