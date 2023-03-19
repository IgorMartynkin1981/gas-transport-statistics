package ru.alrosa.transport.gastransportstatistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alrosa.transport.gastransportstatistics.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
