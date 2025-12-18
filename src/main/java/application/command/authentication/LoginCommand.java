package application.command.authentication;

import application.command.Command;

public record LoginCommand(String username, String password) implements Command {
}
