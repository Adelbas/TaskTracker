package ru.adel.tasktracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.adel.tasktracker.dto.TaskRequest;
import ru.adel.tasktracker.exception.TaskNotFoundException;
import ru.adel.tasktracker.model.Task;
import ru.adel.tasktracker.service.TaskService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getTasks(@RequestParam(required = false, name = "interval") String interval,
                               @RequestParam(required = false, name = "completed") String filter){
        return taskService.getTasks(interval, filter);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@Valid @RequestBody TaskRequest taskRequest){
        return taskService.createTask(taskRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeTask(@PathVariable("id") Long id) throws TaskNotFoundException {
        taskService.deleteTask(id);
    }

    @DeleteMapping("/delete-all")
    @ResponseStatus(HttpStatus.OK)
    public void removeAllTask() {
        taskService.deleteAllTask();
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTask(@Valid @RequestBody TaskRequest taskRequest, @PathVariable("id") Long id) throws TaskNotFoundException {
        return taskService.editTask(taskRequest, id);
    }

    @PutMapping("/updateMark/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTaskMark(@PathVariable("id") Long id,
                               @RequestParam("completed") boolean isCompleted) throws TaskNotFoundException {
        return taskService.editTaskMark(isCompleted, id);
    }
}
