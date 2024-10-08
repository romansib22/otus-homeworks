package ru.romansib.otus;

import java.util.Comparator;
import java.util.List;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {
        List<TaskStatus> statusOrderList = TaskService.statusOrderList;
        int result = 0;
        int t1Index = statusOrderList.indexOf(t1.getTaskStatus());
        int t2Index = statusOrderList.indexOf(t2.getTaskStatus());
        if (t1Index < 0 && t2Index < 0)
            result = t1.getTaskStatus().compareTo(t2.getTaskStatus());
        else if (t1Index < 0)
            result = 1;
        else if (t2Index < 0)
            result = -1;
        else
            result = t1Index - t2Index;
        return result;
    }
}
