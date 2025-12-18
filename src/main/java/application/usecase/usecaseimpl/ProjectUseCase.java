package application.usecase.usecaseimpl;

import application.command.Command;

import java.util.UUID;

public interface ProjectUseCase<C extends Command, V> {
    V execute(C command,  UUID projectId);
}
