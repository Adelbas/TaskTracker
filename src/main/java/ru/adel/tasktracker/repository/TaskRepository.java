package ru.adel.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adel.tasktracker.model.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompletionDateIsBetween(LocalDateTime from, LocalDateTime to);

    List<Task> findByCompletionDateIsBetweenAndCompleted(LocalDateTime from, LocalDateTime to, boolean completed);

    List<Task> findByCompleted(boolean completed);
}
