package ru.adel.tasktracker.service;

import ru.adel.tasktracker.dto.UserRequest;
import ru.adel.tasktracker.model.User;

import java.util.List;

public interface UserService {
    User getCurrentUser();
    List<User> getAllUsers();
    User getUser(Long id);
    User getUser(String email);
    User disableUser(Long id);
    User enableUser(Long id);
    void deleteById(Long id);
    User updateCurrentUser(UserRequest userRequest);
}
