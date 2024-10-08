package ru.romansib.otus;

public class Task {
    private int id;
    private String taskName;
    private TaskStatus taskStatus;

    public Task(int id, String taskName, TaskStatus taskStatus) {
        this.id = id;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
