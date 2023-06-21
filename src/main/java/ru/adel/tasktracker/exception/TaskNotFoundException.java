package ru.adel.tasktracker.exception;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(Long id){
        super("Task not found with id "+id);
    }
}
