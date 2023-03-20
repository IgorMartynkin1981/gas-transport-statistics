package ru.alrosa.transport.gastransportstatistics.services;

import ru.alrosa.transport.gastransportstatistics.entity.Task;

import java.util.Collection;

public interface TaskService {
    Collection<Task> getAllTasks(String periodStart, String periodEnd);

    Task getTaskById(Long id);

    Task updateTask(Task task);

    Task createTask(Task task);

    void deactivationTask(Long taskId);
}
