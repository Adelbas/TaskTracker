package ru.adel.tasktracker.service;

import ru.adel.tasktracker.dto.TaskRequest;
import ru.adel.tasktracker.model.Task;
import java.util.List;

public interface TaskService {
    Task getTaskById(Long id);
    Task createTask(TaskRequest taskRequest);
    void deleteTask(Long id);
    Task updateTask(Long id, TaskRequest taskRequest);
    Task updateTaskMark(Long id, boolean isCompleted);
    List<Task> getAllTasks(String interval, Boolean completed);
    List<Task> getAuthenticatedUserTasks(String interval, Boolean completed);
    List<Task> getUserTasks(String interval, Boolean completed, Long userId);

}
