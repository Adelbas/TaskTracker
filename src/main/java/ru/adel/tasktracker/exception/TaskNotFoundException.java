package ru.adel.tasktracker.exception;

import lombok.NonNull;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(@NonNull Long id){
        super("Task not found with id "+id);
    }
}
