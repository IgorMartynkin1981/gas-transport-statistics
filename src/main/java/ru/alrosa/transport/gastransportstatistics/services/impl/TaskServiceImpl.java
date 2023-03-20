package ru.alrosa.transport.gastransportstatistics.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.entity.Task;
import ru.alrosa.transport.gastransportstatistics.entity.enums.TaskStatus;
import ru.alrosa.transport.gastransportstatistics.exception.DataNotFound;
import ru.alrosa.transport.gastransportstatistics.repositories.TaskRepository;
import ru.alrosa.transport.gastransportstatistics.serializationdeserialization.UtilClass;
import ru.alrosa.transport.gastransportstatistics.services.TaskService;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Collection<Task> getAllTasks(String periodStart, String periodEnd) {
        return taskRepository
                .findTasksByCreationTimeBetween(UtilClass.toLocalDateTime(periodStart),
                        UtilClass.toLocalDateTime(periodEnd));
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new DataNotFound(
                String.format("Task with id %d was not found in the database", id)));
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task createTask(Task task) {
        task.setStatus(TaskStatus.OPEN);
        task.setCreationTime(LocalDate.now());
        return taskRepository.save(task);
    }

    @Override
    public void deactivationTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new DataNotFound(
                String.format("Task with id %d was not found in the database", taskId)));
        task.setStatus(TaskStatus.REJECTED);
        taskRepository.save(task);
    }
}
