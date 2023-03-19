package ru.alrosa.transport.gastransportstatistics.services;

import ru.alrosa.transport.gastransportstatistics.entity.Task;

public interface TaskService {
    Task getAllTasks();

    Task getTaskById(Long id);

    Task updateTask(Task task);

    Task createTask(Task task);

    void deactivationTask(Long taskId);
}
