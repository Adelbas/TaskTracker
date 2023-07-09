package ru.adel.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.adel.tasktracker.model.Task;
import ru.adel.tasktracker.model.User;
import ru.adel.tasktracker.service.TaskService;
import ru.adel.tasktracker.service.UserService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/all-users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/all-tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasks(@RequestParam(required = false, name = "interval") String interval,
                                  @RequestParam(required = false, name = "completed") Boolean completed){
        return taskService.getAllTasks(interval, completed);
    }

    @GetMapping("/user-tasks/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getUserTaskById(@PathVariable("user_id") Long id,
                                      @RequestParam(required = false, name = "interval") String interval,
                                      @RequestParam(required = false, name = "completed") Boolean completed){
        return taskService.getUserTasks(interval,completed,id);
    }

    @PutMapping("/ban/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User banUser(@PathVariable("id") Long id){
        return userService.disableUser(id);
    }

    @PutMapping("/unban/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User unbanUser(@PathVariable("id") Long id){
        return userService.enableUser(id);
    }
}
