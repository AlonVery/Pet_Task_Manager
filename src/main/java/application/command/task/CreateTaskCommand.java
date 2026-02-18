package application.command.task;

import application.command.Command;
import domain.model.user.User;

public record CreateTaskCommand(String projectName, User user, String titleTask) implements Command {
}
