package ru.adel.tasktracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.adel.tasktracker.dto.TaskRequest;
import ru.adel.tasktracker.exception.TaskNotFoundException;
import ru.adel.tasktracker.model.Task;
import ru.adel.tasktracker.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task createTask(TaskRequest taskRequest){
        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .completed(taskRequest.isCompleted())
                .completionDate(taskRequest.getCompletionDate())
                .build();
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteAllTask() {
        taskRepository.deleteAll();
    }

    @Override
    public Task editTask(Long id,TaskRequest taskRequest) {
        Task existedTask = taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
        existedTask.setTitle(taskRequest.getTitle());
        existedTask.setDescription(taskRequest.getDescription());
        existedTask.setCompleted(taskRequest.isCompleted());
        return taskRepository.save(existedTask);
    }

    @Override
    public Task editTaskMark(Long id, boolean isCompleted) {
        Task existedTask = taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
        existedTask.setCompleted(isCompleted);
        return taskRepository.save(existedTask);
    }

    @Override
    public List<Task> getTasks(String interval, Boolean completed){
        List<Task> tasks;
        if(interval!=null && !interval.isEmpty()) {
            LocalDateTime dateFrom = LocalDateTime.now().toLocalDate().atStartOfDay();
            LocalDateTime dateTo;
            switch (interval) {
                case "today" -> dateTo = dateFrom.toLocalDate().atTime(23,59);
                case "week" -> dateTo = dateFrom.toLocalDate().plusWeeks(1).minusDays(1).atTime(23,59);
                case "month" -> dateTo = dateFrom.toLocalDate().plusMonths(1).minusDays(1).atTime(23,59);
                default -> throw new IllegalArgumentException("Invalid 'interval' parameter!");
            }
            tasks =  taskRepository.findByCompletionDateIsBetween(dateFrom, dateTo);
        } else tasks = taskRepository.findAll();

        if (completed!=null){
            if (completed)
                return tasks.stream().filter(Task::isCompleted).collect(Collectors.toList());
            else
                return tasks.stream().filter(task -> !task.isCompleted()).collect(Collectors.toList());
        }
        return tasks;
    }
}
