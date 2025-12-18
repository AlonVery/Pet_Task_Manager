package application.command.project_command;

import application.command.Command;

public record CreateProjectCommand(String projectName) implements Command {
}
