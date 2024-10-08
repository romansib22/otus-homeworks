package ru.romansib.otus;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class ProcessingService {
    public static AtomicInteger runCounter = new AtomicInteger(0);
    private int threadCount = 3;
    private final ExecutorService executorService;

    public ProcessingService() {
        this.executorService = Executors.newFixedThreadPool(threadCount);
        ;
    }

    public void startProcessing(LinkedList<Integer> taskList) {
        try {
            processTasks(taskList);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (RejectedExecutionException ree) {
            System.out.println("Задания больше не принимаются!");
        }
    }

    private void processTasks(LinkedList<Integer> taskList) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(taskList.size());
        taskList.forEach(task -> executorService.submit(new Runnable() {
            @Override
            public void run() {
                doWork(task);
                latch.countDown();
            }
        }));
        latch.await();

        System.out.println("Всего обработано " + runCounter.get());
    }

    private void doWork(int param) {
        try {
            Thread.sleep(30);
            System.out.println(Thread.currentThread().getName() + " " + param + " counter " + runCounter.incrementAndGet());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedList<Integer> genTasks(int from, int to) {
        LinkedList<Integer> taskList = new LinkedList<>();
        for (int i = from; i < to; i++) {
            taskList.add(i);
        }
        return taskList;
    }

    public void shutdownThreadPool() {
        executorService.shutdown();
    }
}
