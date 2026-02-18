package application.command.registration;

import application.command.Command;

public record RegisterCommand(String userName, String email, String password) implements Command {
}
