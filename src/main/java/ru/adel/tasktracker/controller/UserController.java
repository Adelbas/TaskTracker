package ru.adel.tasktracker.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.adel.tasktracker.dto.UserRequest;
import ru.adel.tasktracker.model.User;
import ru.adel.tasktracker.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public User getAuthenticatedUser(){
        return userService.getCurrentUser();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public User updateAuthenticatedUser(@Valid @RequestBody UserRequest userRequest){
        return userService.updateCurrentUser(userRequest);
    }

}
