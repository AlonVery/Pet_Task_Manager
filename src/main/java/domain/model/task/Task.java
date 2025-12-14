package domain.model.task;

import domain.event.DomainEvent;
import domain.event.task.TaskCompletedEvent;
import domain.event.task.TaskStartedEvent;
import domain.model.user.User;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Getter
@NoArgsConstructor(force = true)
public class Task {
    private final UUID taskId;

    @Setter
    private User taskCreator;
    @Setter
    private String taskTitle;

    private TaskStatus taskStatus;
    private final Instant createdAt;

    @Setter
    private Instant updatedAt;

    private static final List<DomainEvent> events = new ArrayList<>();

    protected Task(UUID taskId, User taskCreator, String taskTitle) {
        this.taskId = taskId;
        this.taskCreator = Optional.ofNullable(taskCreator).orElseThrow();
        this.taskTitle = Optional.ofNullable(taskTitle).orElse("Title cannot be empty");
        this.taskStatus = TaskStatus.CREATED;
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
    }

    @SneakyThrows
    public void startTask(User userStarted) {
        if (taskStatus != TaskStatus.CREATED) throw new IllegalStateException("Task must be in CREATED state");
        if (!taskCreator.equals(userStarted)) {
            throw new IllegalAccessException("Not valid author");
        };
        taskStatus = TaskStatus.STARTED;
        updatedAt = Instant.now();
        events.add(new TaskStartedEvent(taskId, taskCreator));
    }

    @SneakyThrows
    public void completedTask(User userStarted) {
        if (taskStatus != TaskStatus.STARTED) throw new IllegalStateException("Task must be in started for finish");
        if (!taskCreator.equals(userStarted)) {
            throw new IllegalAccessException("Not valid author");
        }
        taskStatus = TaskStatus.COMPLETED;
        updatedAt = Instant.now();
        events.add(new TaskCompletedEvent(taskId, taskCreator));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskId, task.taskId) &&
                Objects.equals(taskCreator, task.taskCreator) &&
                Objects.equals(createdAt, task.createdAt) &&
                Objects.equals(updatedAt, task.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, taskCreator, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId + "\n" +
                ", taskCreator=" + taskCreator + "\n" +
                ", taskTitle=" + taskTitle + "\n" +
                ", taskStatus=" + taskStatus + "\n" +
                ", createdAt=" + createdAt + "\n" +
                ", updatedAt=" + updatedAt +
                '}';
    }
}