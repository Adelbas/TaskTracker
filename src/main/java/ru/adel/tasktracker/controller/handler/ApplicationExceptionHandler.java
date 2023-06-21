package ru.adel.tasktracker.controller.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.adel.tasktracker.exception.ErrorResponse;
import ru.adel.tasktracker.exception.TaskNotFoundException;

import java.time.format.DateTimeParseException;
import java.util.HashMap;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error(e.getMessage(),e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed!")
                .path(request.getRequestURI())
                .details(new HashMap<>())
                .build();
        e.getBindingResult().getFieldErrors().forEach(error->errorResponse.getDetails().put(error.getField(),error.getDefaultMessage()));
        return errorResponse;
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFoundException(TaskNotFoundException e, HttpServletRequest request) {
        log.error(e.getMessage(),e);
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getLocalizedMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateTimeParseException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(),e);
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getLocalizedMessage())
                .path(request.getRequestURI())
                .build();
    }

//    @ExceptionHandler(DateTimeParseException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleDateTimeParseException(DateTimeParseException e, HttpServletRequest request) {
//        log.error(e.getMessage(),e);
//        return ErrorResponse.builder()
//                .status(HttpStatus.BAD_REQUEST.value())
//                .message(e.getLocalizedMessage())
//                .path(request.getRequestURI())
//                .build();
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
//        log.error(e.getMessage(),e);
//        return ErrorResponse.builder()
//                .status(HttpStatus.BAD_REQUEST.value())
//                .message(e.getLocalizedMessage())
//                .path(request.getRequestURI())
//                .build();
//    }
}
