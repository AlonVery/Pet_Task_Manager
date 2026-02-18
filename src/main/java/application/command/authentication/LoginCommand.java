package application.command.authentication;

import application.command.Command;

public record LoginCommand(String email, String password) implements Command {
}
