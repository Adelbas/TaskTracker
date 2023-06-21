package ru.adel.tasktracker.service;

import ru.adel.tasktracker.dto.TaskRequest;
import ru.adel.tasktracker.exception.TaskNotFoundException;
import ru.adel.tasktracker.model.Task;
import java.util.List;

public interface TaskService {

    Task createTask(TaskRequest taskRequest);

    void deleteTask(Long id) throws TaskNotFoundException;

    void deleteAllTask();

    Task editTask(TaskRequest taskRequest, Long id) throws TaskNotFoundException;

    Task editTaskMark(boolean isCompleted, Long id) throws TaskNotFoundException;

    List<Task> getTasks(String interval, String filter);
}
