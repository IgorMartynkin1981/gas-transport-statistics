package ru.alrosa.transport.gastransportstatistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alrosa.transport.gastransportstatistics.entity.Task;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Collection<Task> findTasksByCreationTimeBetween(LocalDate periodStart, LocalDate periodEnd);
}
