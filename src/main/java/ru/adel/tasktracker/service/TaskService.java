package ru.adel.tasktracker.service;

import ru.adel.tasktracker.dto.TaskRequest;
import ru.adel.tasktracker.model.Task;
import java.util.List;

public interface TaskService {

    Task createTask(TaskRequest taskRequest);

    void deleteTask(Long id);

    void deleteAllTask();

    Task editTask(Long id, TaskRequest taskRequest);

    Task editTaskMark(Long id, boolean isCompleted);

    List<Task> getTasks(String interval, Boolean completed);
}
