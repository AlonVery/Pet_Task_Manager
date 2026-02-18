package application.usecase.usecaseimpl;

import application.command.Command;

public interface CommandUseCase<C extends Command> {
    void execute(C command);
}
