package ru.adel.tasktracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
//    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "completion_date")
    private LocalDateTime completionDate;

//    @JsonProperty(value = "isCompleted",access = JsonProperty.Access.READ_ONLY)
    @Column(name = "is_completed")
    private boolean completed;

}
