package ru.romansib.otus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.romansib.otus.TaskStatus.*;

public class TaskService {
    public static final List<Task> taskList = List.of(new Task(1, "Task #1", CLOSED),
            new Task(2, "Task #2", NEW),
            new Task(3, "Task #3", IN_PROGRESS),
            new Task(4, "Task #4", IN_PROGRESS),
            new Task(5, "Task #5", CLOSED),
            new Task(6, "Task #6", IN_PROGRESS),
            new Task(7, "Task #7", NEW),
            new Task(8, "Task #8", IN_PROGRESS),
            new Task(9, "Task #9", NEW),
            new Task(10, "Task #10", CLOSED));

    public static List<TaskStatus> statusOrderList = List.of(NEW, IN_PROGRESS, CLOSED);

    public List<Task> getTasksByStatus(Stream<Task> taskStream, TaskStatus status) {
        //Получение списка задач по выбранному статусу;
        return taskStream
                .filter(task -> task.getTaskStatus() == status)
                .collect(Collectors.toList());
    }

    public boolean checkTaskExistsById(Stream<Task> taskStream, int taskId) {
        //Проверка наличия задачи с указанным ID;
        return taskStream.anyMatch(task -> task.getId() == taskId);
    }

    public List<Task> getSortedTaskListByStatus(Stream<Task> taskStream) {
        //Получение списка задач в отсортированном по статусу виде: открыта, в работе, закрыта
        //return taskStream.sorted(Comparator.comparing(Task::getTaskStatus)).collect(Collectors.toList());
        return taskStream.sorted(new TaskComparator()).collect(Collectors.toList());
    }


    public Map<TaskStatus, List<Task>> getSortedTaskListByStatus2(Stream<Task> taskStream) {
        //Получение списка задач в отсортированном по статусу виде: открыта, в работе, закрыта
        return taskStream.collect(Collectors.groupingBy(Task::getTaskStatus, HashMap::new, Collectors.toList()));
    }

    public int getCountTasksByStatus(Stream<Task> taskStream, TaskStatus status) {
        //Подсчет количества задач по определенному статусу.
        return (int) taskStream.filter(task -> task.getTaskStatus() == status).count();

    }

    public Map<TaskStatus, Long> getCountTasksByStatus2(Stream<Task> taskStream) {
        //Подсчет количества задач по определенному статусу.
        //return taskStream.sorted(new TaskComparator()).collect(Collectors.groupingBy(Task::getTaskStatus, Collectors.counting()));
        Map<TaskStatus, Long> statusCountMap = taskStream.collect(Collectors.groupingBy(Task::getTaskStatus, Collectors.counting()));
        //Эксперимента ради, добавил сортировку по статусу.
        Map<TaskStatus, Long> result = new LinkedHashMap<>();
        statusCountMap.entrySet().stream().sorted(Map.Entry.comparingByKey(new TaskStatusComparator())).forEachOrdered(o -> result.put(o.getKey(), o.getValue()));
        return result;
    }
}
