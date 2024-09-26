package ru.romansib.otus;

public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();

        System.out.println("Tasks with status NEW:");
        System.out.println(taskService.getTasksByStatus(TaskStatus.NEW));
        System.out.println("Tasks with status IN_PROGRESS:");
        System.out.println(taskService.getTasksByStatus(TaskStatus.IN_PROGRESS));
        System.out.println("Tasks with status CLOSED:");
        System.out.println(taskService.getTasksByStatus(TaskStatus.CLOSED));

        System.out.println("-----------------------------");

        System.out.println("Check task with id 5 exists (exists):");
        System.out.println(taskService.checkTaskExistsById(5));

        System.out.println("Check task with id 50 exists (not exists):");
        System.out.println(taskService.checkTaskExistsById(50));

        System.out.println("-----------------------------");
        System.out.println("Sorted task list by status:");
        System.out.println(taskService.getSortedTaskListByStatus());

        System.out.println("Sorted task list by status second realization:");
        System.out.println(taskService.getSortedTaskListByStatus2());

        System.out.println("-----------------------------");
        System.out.println("Number of tasks with status NEW:");
        System.out.println(taskService.getCountTasksByStatus(TaskStatus.NEW));
        System.out.println("Number of tasks with status IN_PROGRESS:");
        System.out.println(taskService.getCountTasksByStatus(TaskStatus.IN_PROGRESS));
        System.out.println("Number of tasks with status CLOSED:");
        System.out.println(taskService.getCountTasksByStatus(TaskStatus.CLOSED));

        System.out.println("Number of tasks with status second realization:");
        System.out.println(taskService.getCountTasksByStatus2());
    }


}