package org.example;

import org.example.annotations.AfterSuite;
import org.example.annotations.BeforeSuite;
import org.example.annotations.Disabled;
import org.example.annotations.Test;
import org.example.exceptions.NotValidTestSuiteException;

import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {
    public static void run(Class testSuiteClass) {
        int testsCount = 0;
        try {
            testsCount = checkTestSuiteClass(testSuiteClass);
        } catch (NotValidTestSuiteException e) {
            System.out.println(e);
            return;
        }

        TreeMap<Integer, List<Method>> runOrderMap = new TreeMap<>(Comparator.reverseOrder());
        for (Method m : testSuiteClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                runOrderMap.put(20, Collections.singletonList(m));
            }
            if (m.isAnnotationPresent(AfterSuite.class)) {
                runOrderMap.put(0, Collections.singletonList(m));
            }
            if (m.isAnnotationPresent(Test.class)) {
                Test annotation = m.getAnnotation(Test.class);
                List<Method> methodList = runOrderMap.getOrDefault(annotation.priority(), new ArrayList<>());
                methodList.add(m);
                runOrderMap.put(annotation.priority(), methodList);
            }
        }
        int successCount = 0;
        int failedCount = 0;
        for (List<Method> methodList : runOrderMap.values()) {
            for (Method method : methodList) {
                boolean result = runMethod(method);
                if (result) {
                    failedCount++;
                } else {
                    successCount++;
                }
            }
        }
        System.out.println("Total tests " + testsCount);
        System.out.println("Successfully tests " + successCount);
        System.out.println("Failed tests " + failedCount);
    }

    private static boolean runMethod(Method m) {
        boolean testFailed = false;
        try {
            m.invoke(null);
        } catch (Exception e) {
            testFailed = true;
        }
        return testFailed;
    }

    private static int checkTestSuiteClass(Class testSuiteClass) throws NotValidTestSuiteException {
        int commonCounter = 0;
        int beforeCount = 0;
        int afterCount = 0;
        for (Method m : testSuiteClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Disabled.class)) {
                System.out.println("Method " + m.getName() + " disabled with message: "+ m.getAnnotation(Disabled.class).message());
                continue;
            }
            boolean hasAnnotation = false;
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                hasAnnotation = true;
                beforeCount++;
            }
            if (m.isAnnotationPresent(AfterSuite.class) && hasAnnotation) {
                throw new NotValidTestSuiteException("Method " + m.getName() + " has two annotations");
            } else if (m.isAnnotationPresent(AfterSuite.class) && !hasAnnotation) {
                hasAnnotation = true;
                afterCount++;
            }
            if (m.isAnnotationPresent(Test.class) && hasAnnotation) {
                throw new NotValidTestSuiteException("Method " + m.getName() + " has two annotations");
            } else if (m.isAnnotationPresent(Test.class) && !hasAnnotation) {
                hasAnnotation = true;
            }
            if (hasAnnotation) {
                commonCounter++;
            }
        }
        if (beforeCount > 1) {
            throw new NotValidTestSuiteException("Class " + testSuiteClass.getSimpleName() + " has " + beforeCount + " BeforeSuite annotations");
        }
        if (afterCount > 1) {
            throw new NotValidTestSuiteException("Class " + testSuiteClass.getSimpleName() + " has " + afterCount + " AfterSuite annotations");
        }
        return commonCounter;
    }
}
