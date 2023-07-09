package ru.adel.tasktracker.exception;

import lombok.NonNull;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(@NonNull String message) {
        super(message);
    }
}
