package application.command.project_command;

import application.command.Command;

import java.util.UUID;

public record DeleteProjectCommand(UUID projectId) implements Command {
}