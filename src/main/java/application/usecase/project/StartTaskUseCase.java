package application.usecase.project;

import application.command.task.StartTaskCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.model.Project;

public class StartTaskUseCase implements UseCase<StartTaskCommand, Void> {

    private Project project;

    @Override
    public Void execute(StartTaskCommand command) {
        project.startTask(command.taskId(), command.user());
        return null;
    }
}
