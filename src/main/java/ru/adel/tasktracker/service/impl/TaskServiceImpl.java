package ru.adel.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.adel.tasktracker.dto.TaskRequest;
import ru.adel.tasktracker.exception.TaskNotFoundException;
import ru.adel.tasktracker.model.Task;
import ru.adel.tasktracker.repository.TaskRepository;
import ru.adel.tasktracker.service.TaskService;
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
                .creationDate(LocalDateTime.now())
                .completionDate(taskRequest.getCompletionDate())
                .build();
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) throws TaskNotFoundException {
        taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteAllTask() {
        taskRepository.deleteAll();
    }

    @Override
    public Task editTask(TaskRequest taskRequest, Long id) throws TaskNotFoundException {
        Task existedTask = taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
        existedTask.setTitle(taskRequest.getTitle());
        existedTask.setDescription(taskRequest.getDescription());
        existedTask.setCompleted(taskRequest.isCompleted());
        return taskRepository.save(existedTask);
    }

    @Override
    public Task editTaskMark(boolean isCompleted, Long id) throws TaskNotFoundException {
        Task existedTask = taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException(id));
        existedTask.setCompleted(isCompleted);
        return taskRepository.save(existedTask);
    }

    @Override
    public List<Task> getTasks(String interval, String filter){
        List<Task> tasks;
        if(interval!=null && !interval.isEmpty()) {
            LocalDateTime dateTo;
            switch (interval) {
                case "today" -> dateTo = LocalDateTime.now().plusDays(1);
                case "week" -> dateTo = LocalDateTime.now().plusWeeks(1);
                case "month" -> dateTo = LocalDateTime.now().plusMonths(1);
                default -> throw new IllegalArgumentException("Invalid 'interval' parameter!");
            }
            tasks =  taskRepository.findByCompletionDateIsBetween(LocalDateTime.now().toLocalDate().atStartOfDay(), dateTo);
        } else tasks = taskRepository.findAll();

        if(filter!=null && !filter.isEmpty()){
            return switch (filter) {
                case "true" -> tasks.stream().filter(Task::isCompleted).collect(Collectors.toList());
                case "false" -> tasks.stream().filter(task -> !task.isCompleted()).collect(Collectors.toList());
                default -> throw new IllegalArgumentException("Invalid 'completed' parameter!");
            };
        }
        return tasks;
    }
}
