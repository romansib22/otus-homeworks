package org.example;

import org.example.annotations.AfterSuite;
import org.example.annotations.BeforeSuite;
import org.example.annotations.Disabled;
import org.example.annotations.Test;

public class TestSuite {

    @BeforeSuite
    public static void init() {
        System.out.println("processing @BeforeSuite annotation");
    }

    @Test
    public static void test1() {
        System.out.println("processing test1 with default priority");
    }

    @Test
    public static void test2() {
        System.out.println("processing test2 with default priority");
    }

    @Test(priority = 10)
    public static void test3() {
        System.out.println("processing test3 with max priority");
    }

    @Test(priority = 7)
    public static void test4() {
        System.out.println("processing test4 with priority = 7");
    }

    @Test(priority = 2)
    public static void test5() {
        System.out.println("processing test5 with priority = 2");
    }

    @Test(priority = 9)
    @Disabled(message = "test with priority 9 has been disabled")
    public static void testDisabled() {
        System.out.println("processing testDisabled with priority = 9");
    }
    @AfterSuite
    public static void finish() {
        System.out.println("processing @AfterSuite annotation");
    }
}
