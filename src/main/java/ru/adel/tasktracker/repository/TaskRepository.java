package ru.adel.tasktracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.adel.tasktracker.model.Task;
import ru.adel.tasktracker.model.User;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    List<Task> findByCompletionDateIsBetween(LocalDateTime from, LocalDateTime to);
    List<Task> findByCompletionDateIsBetweenAndUser(LocalDateTime from, LocalDateTime to, User user);
}
