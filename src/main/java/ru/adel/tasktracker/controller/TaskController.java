package ru.adel.tasktracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.adel.tasktracker.dto.TaskRequest;
import ru.adel.tasktracker.model.Task;
import ru.adel.tasktracker.service.TaskService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAuthenticatedUserTasks(@RequestParam(required = false, name = "interval") String interval,
                                                @RequestParam(required = false, name = "completed") Boolean completed){
        return taskService.getAuthenticatedUserTasks(interval, completed);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getAuthenticatedUserTaskById(@PathVariable("id") Long id){
        return taskService.getTaskById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@Valid @RequestBody TaskRequest taskRequest){
        return taskService.createTask(taskRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTask(@PathVariable("id") Long id,
                           @Valid @RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(id,taskRequest);
    }

    @PutMapping("/update-mark/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTaskMark(@PathVariable("id") Long id,
                               @RequestParam("completed") boolean isCompleted) {
        return taskService.updateTaskMark(id, isCompleted);
    }
}
