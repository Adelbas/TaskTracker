package ru.adel.tasktracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.adel.tasktracker.dto.TaskRequest;
import ru.adel.tasktracker.exception.TaskNotFoundException;
import ru.adel.tasktracker.model.Task;
import ru.adel.tasktracker.model.User;
import ru.adel.tasktracker.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static ru.adel.tasktracker.model.enums.Role.ADMIN;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public Task getTaskById(Long id) {
        var task = taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
        var user = userService.getCurrentUser();
        if (!task.getUser().equals(user) && !user.getRole().equals(ADMIN))
            throw new TaskNotFoundException(id);
        return task;
    }

    @Override
    public Task createTask(TaskRequest taskRequest){
        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .completed(taskRequest.isCompleted())
                .completionDate(taskRequest.getCompletionDate())
                .user(userService.getCurrentUser())
                .build();
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        var task = getTaskById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public Task updateTask(Long id,TaskRequest taskRequest) {
        Task existedTask = getTaskById(id);
        existedTask.setTitle(taskRequest.getTitle());
        existedTask.setDescription(taskRequest.getDescription());
        existedTask.setCompleted(taskRequest.isCompleted());
        return taskRepository.save(existedTask);
    }

    @Override
    public Task updateTaskMark(Long id, boolean isCompleted) {
        Task existedTask = getTaskById(id);
        existedTask.setCompleted(isCompleted);
        return taskRepository.save(existedTask);
    }

    @Override
    public List<Task> getAllTasks(String interval, Boolean completed){
        List<Task> tasks;
        if(interval!=null && !interval.isEmpty()) {
            LocalDateTime dateFrom = LocalDateTime.now().toLocalDate().atStartOfDay();
            LocalDateTime dateTo = getDateTo(dateFrom, interval);

            tasks =  taskRepository.findByCompletionDateIsBetween(dateFrom, dateTo);
        } else
            tasks = taskRepository.findAll();

        return filterCompleted(completed, tasks);
    }

    @Override
    public List<Task> getAuthenticatedUserTasks(String interval, Boolean completed) {
        User user = userService.getCurrentUser();
        return getUserTasks(interval, completed, user);
    }

    @Override
    public List<Task> getUserTasks(String interval, Boolean completed, Long userId) {
        User user = userService.getUser(userId);
        return getUserTasks(interval, completed, user);
    }

    private List<Task> getUserTasks(String interval, Boolean completed, User user) {
        List<Task> tasks;
        if(interval!=null && !interval.isEmpty()) {
            LocalDateTime dateFrom = LocalDateTime.now().toLocalDate().atStartOfDay();
            LocalDateTime dateTo = getDateTo(dateFrom, interval);

            tasks = taskRepository.findByCompletionDateIsBetweenAndUser(dateFrom, dateTo, user);
        } else
            tasks = taskRepository.findByUser(user);

        return filterCompleted(completed,tasks);
    }

    private LocalDateTime getDateTo(LocalDateTime dateFrom, String interval){
        return switch (interval) {
            case "today" -> dateFrom.toLocalDate().atTime(23,59);
            case "week" -> dateFrom.toLocalDate().plusWeeks(1).minusDays(1).atTime(23,59);
            case "month" -> dateFrom.toLocalDate().plusMonths(1).minusDays(1).atTime(23,59);
            default -> throw new IllegalArgumentException("Invalid 'interval' parameter!");
        };
    }

    private List<Task> filterCompleted(Boolean completed, List<Task> tasks) {
        if (completed==null)
            return tasks;
        else if (completed)
            return tasks.stream().filter(Task::isCompleted).collect(Collectors.toList());
        return tasks.stream().filter(task -> !task.isCompleted()).collect(Collectors.toList());
    }
}
