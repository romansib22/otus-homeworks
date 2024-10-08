package ru.romansib.otus;

import java.util.Comparator;
import java.util.List;

public class TaskStatusComparator implements Comparator<TaskStatus> {

    @Override
    public int compare(TaskStatus s1, TaskStatus s2) {
        List<TaskStatus> statusOrderList = TaskService.statusOrderList;
        int result = 0;
        int s1Index = statusOrderList.indexOf(s1);
        int s2Index = statusOrderList.indexOf(s2);
        if (s1Index < 0 && s2Index < 0)
            result = s1.compareTo(s2);
        else if (s1Index < 0)
            result = 1;
        else if (s2Index < 0)
            result = -1;
        else
            result = s1Index - s2Index;
        return result;
    }
}
