package ru.adel.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskRequest {

    @NotBlank(message = "Empty title!")
    private String title;

    private String description;

    @NotNull(message = "Empty completion date!")
    @Future(message = "Completion date must be a date in the future!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime completionDate;

    private boolean completed;
}
