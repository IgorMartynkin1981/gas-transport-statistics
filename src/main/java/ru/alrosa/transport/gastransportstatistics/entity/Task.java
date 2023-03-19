package ru.alrosa.transport.gastransportstatistics.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.alrosa.transport.gastransportstatistics.entity.enums.TaskStatus;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Simple domain object that represents application tasks from protocol.
 *
 * @author Igor Martynkin
 * @version 1.0
 */

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner; // создатель задачи

    @ManyToOne
    @JoinColumn(name = "responsible_executor_id", nullable = false)
    private User responsibleExecutor; // ответственный исполнитель

    @Column(name = "creation_time", nullable = false)
    private LocalDate creationTime;

    @Column(name = "period_of_execution", nullable = false)
    private LocalDate periodOfExecution;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Column(nullable = false)
    private String task;

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", owner=" + owner +
                ", responsibleExecutor=" + responsibleExecutor +
                ", creationTime=" + creationTime +
                ", periodOfExecution=" + periodOfExecution +
                ", status=" + status +
                ", task='" + task + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return id.equals(task1.id) && owner.equals(task1.owner)
                && responsibleExecutor.equals(task1.responsibleExecutor)
                && creationTime.equals(task1.creationTime) && periodOfExecution.equals(task1.periodOfExecution)
                && status == task1.status && task.equals(task1.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, responsibleExecutor, creationTime, periodOfExecution, status, task);
    }
}
