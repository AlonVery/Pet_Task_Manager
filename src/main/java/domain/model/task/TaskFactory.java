package domain.model.task;

import domain.model.user.User;

import java.util.UUID;

public class TaskFactory {

    public Task createTask(User user, String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        return new Task(UUID.randomUUID(), user, title);
    }
}
