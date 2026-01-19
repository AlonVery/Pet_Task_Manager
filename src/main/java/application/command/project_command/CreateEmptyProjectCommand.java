package application.command.project_command;

import application.command.Command;

public record CreateEmptyProjectCommand(String email, String projectName) implements Command {
}
