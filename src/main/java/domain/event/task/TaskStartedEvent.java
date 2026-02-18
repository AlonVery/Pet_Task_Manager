package domain.event.task;

import domain.event.DomainEvent;
import domain.model.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "taskId")
public class TaskStartedEvent implements DomainEvent {
    private final UUID taskId;
    private final User complete_user;
    private final Instant changed = Instant.now();

    public TaskStartedEvent(UUID taskId, User user) {
        this.taskId = taskId;
        this.complete_user = user;
    }

    @Override
    public Instant occurredAt() {
        return changed;
    }
}
