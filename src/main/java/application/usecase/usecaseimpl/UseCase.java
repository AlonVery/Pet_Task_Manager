package application.usecase.usecaseimpl;

import application.command.Command;

public interface UseCase<C extends Command, V> {
    V execute(C command);
}
