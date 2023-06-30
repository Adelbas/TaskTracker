package ru.adel.tasktracker.exception;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.format.DateTimeParseException;
import java.util.HashMap;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e,@NonNull WebRequest request) {
        log.error(e.getMessage(),e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed!")
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .details(new HashMap<>())
                .build();
        e.getBindingResult().getFieldErrors().forEach(error->errorResponse.getDetails().put(error.getField(),error.getDefaultMessage()));
        return errorResponse;
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFoundException(TaskNotFoundException e,@NonNull WebRequest request) {
        log.error(e.getMessage(),e);
        return ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(e.getLocalizedMessage())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .build();
    }

    @ExceptionHandler({DateTimeParseException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateTimeParseAndIllegalArgumentException(RuntimeException e,@NonNull WebRequest request) {
        log.error(e.getMessage(),e);
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getLocalizedMessage())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .build();
    }

}
