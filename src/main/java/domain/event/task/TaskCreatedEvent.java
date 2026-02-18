package domain.event.task;

import domain.event.DomainEvent;
import domain.model.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "taskId")
public class TaskCreatedEvent implements DomainEvent {
    private final UUID taskId;
    private final User user;
    private final Instant changed = Instant.now();

    public TaskCreatedEvent(UUID taskId, User user) {
        this.taskId = taskId;
        this.user = user;
    }

    @Override
    public Instant occurredAt() {
        return changed;
    }
}
