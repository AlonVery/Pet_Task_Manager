package application.command.project_command;

import application.command.Command;

import java.util.UUID;

public record GetProjectCommand(UUID projectId) implements Command {
}
