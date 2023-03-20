package ru.alrosa.transport.gastransportstatistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alrosa.transport.gastransportstatistics.entity.Task;
import ru.alrosa.transport.gastransportstatistics.services.TaskService;

import java.util.Collection;

@RestController
public class ProtocolController {

    private final TaskService taskService;

    @Autowired
    public ProtocolController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Collection<Task> getAllPlans(@RequestParam(name = "periodStart", defaultValue = "1900-01-01")
                                        String periodStart,
                                        @RequestParam(name = "periodEnd", defaultValue = "2200-01-01")
                                        String periodEnd) {
        return taskService.getAllTasks(periodStart, periodEnd);
    }


}
