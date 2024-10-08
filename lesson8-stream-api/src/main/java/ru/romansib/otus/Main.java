package ru.romansib.otus;

public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();

        System.out.println("Tasks with status NEW:");
        System.out.println(taskService.getTasksByStatus(TaskService.taskList.stream(), TaskStatus.NEW));
        System.out.println("Tasks with status IN_PROGRESS:");
        System.out.println(taskService.getTasksByStatus(TaskService.taskList.stream(), TaskStatus.IN_PROGRESS));
        System.out.println("Tasks with status CLOSED:");
        System.out.println(taskService.getTasksByStatus(TaskService.taskList.stream(), TaskStatus.CLOSED));

        System.out.println("-----------------------------");

        System.out.println("Check task with id 5 exists (exists):");
        System.out.println(taskService.checkTaskExistsById(TaskService.taskList.stream(), 5));

        System.out.println("Check task with id 50 exists (not exists):");
        System.out.println(taskService.checkTaskExistsById(TaskService.taskList.stream(), 50));

        System.out.println("-----------------------------");
        System.out.println("Sorted task list by status:");
        System.out.println(taskService.getSortedTaskListByStatus(TaskService.taskList.stream()));

        System.out.println("Sorted task list by status second realization:");
        System.out.println(taskService.getSortedTaskListByStatus2(TaskService.taskList.stream()));

        System.out.println("-----------------------------");
        System.out.println("Number of tasks with status NEW:");
        System.out.println(taskService.getCountTasksByStatus(TaskService.taskList.stream(), TaskStatus.NEW));
        System.out.println("Number of tasks with status IN_PROGRESS:");
        System.out.println(taskService.getCountTasksByStatus(TaskService.taskList.stream(), TaskStatus.IN_PROGRESS));
        System.out.println("Number of tasks with status CLOSED:");
        System.out.println(taskService.getCountTasksByStatus(TaskService.taskList.stream(), TaskStatus.CLOSED));

        System.out.println("Number of tasks with status second realization:");
        System.out.println(taskService.getCountTasksByStatus2(TaskService.taskList.stream()));
    }


}