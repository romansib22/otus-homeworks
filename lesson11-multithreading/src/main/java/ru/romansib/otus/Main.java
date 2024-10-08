package ru.romansib.otus;

public class Main {
    public static void main(String[] args) {
        ProcessingService service = new ProcessingService();

        service.startProcessing(service.genTasks(0,30));
        service.startProcessing(service.genTasks(30,60));

        service.shutdownThreadPool();

        service.startProcessing(service.genTasks(60,90));

        System.out.println("Done");

    }
}