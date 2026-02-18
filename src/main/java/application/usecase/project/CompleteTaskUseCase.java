package application.usecase.project;

import application.command.task.CompleteTaskCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.model.Project;

public class CompleteTaskUseCase implements UseCase<CompleteTaskCommand, Void> {

    private Project project;

    @Override
    public Void execute(CompleteTaskCommand command) {
        project.completeTask(command.taskId(), command.user());
        return null;
    }
}
