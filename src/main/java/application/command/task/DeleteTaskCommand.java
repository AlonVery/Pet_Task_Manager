package application.command.task;

import application.command.Command;
import domain.model.user.User;

import java.util.UUID;

public record DeleteTaskCommand(UUID taskId, User user) implements Command {
}
