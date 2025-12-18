package application.usecase.project;

import application.command.task.DeleteTaskCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.model.Project;

public class DeleteTaskUseCase implements UseCase<DeleteTaskCommand, Void> {

    private Project project;

    @Override
    public Void execute(DeleteTaskCommand command) {
        project.deleteTask(command.taskId(), command.user());
        return null;
    }
}
