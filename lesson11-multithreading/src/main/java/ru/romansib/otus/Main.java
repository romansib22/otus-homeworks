package ru.romansib.otus;

import java.util.LinkedList;

public class Main {
    private static boolean toShutdown = false;
    private static boolean allowShutdown = false;
    private static final LinkedList<Integer> taskList = new LinkedList<Integer>();
    private static final Object monitor = new Object();

    public static void main(String[] args) {
        taskList.addAll(generateTasks(0, 30));
        int threadNum = 3;
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                while (!allowShutdown) {
                        sleep(10);
                        Integer item = getTaskItem();
                        if (item == null) {
                            if (toShutdown) {
                                break;
                            } else {
                                continue;
                            }
                        }
                    System.out.println(Thread.currentThread().getName() + " int= " + item);
                }
            }).start();
        }

        sleep(2000);
        setTaskItem(12345);
        sleep(2000);
        setTaskItems(generateTasks(100, 150));
        shutdown();
        sleep(2000);
        setTaskItem(54321);

    }

    private static Integer getTaskItem() {
        synchronized (monitor) {
            return taskList.poll();
        }
    }

    private static void setTaskItem(int taskInt) {
        if (toShutdown) {
            System.out.println("Поступила команда на выключение. Новые задания не принимаются!");
            return;
        }
        synchronized (monitor) {
            taskList.add(taskInt);
        }
    }

    private static void setTaskItems(LinkedList<Integer> tasks) {
        if (toShutdown) {
            System.out.println("Поступила команда на выключение. Новые задания не принимаются!");
            return;
        }
        synchronized (monitor) {
            taskList.addAll(tasks);
        }
    }

    private static void shutdown() {
        toShutdown = true;
    }

    private static LinkedList<Integer> generateTasks(int from, int to) {
        LinkedList<Integer> taskList = new LinkedList<>();
        for (int i = from; i < to; i++) {
            taskList.add(i);
        }
        return taskList;
    }

    private static void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}