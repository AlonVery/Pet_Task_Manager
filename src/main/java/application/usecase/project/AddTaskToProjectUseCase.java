package application.usecase.project;

import application.usecase.usecaseimpl.ProjectUseCase;
import application.command.task.CreateTaskCommand;
import domain.exception.ProjectNotFoundException;
import domain.model.Project;
import infra.db.jpa_entity.project.ProjectRepository;

import java.util.UUID;

public class AddTaskToProjectUseCase implements ProjectUseCase<CreateTaskCommand, Void> {
    private ProjectRepository projectRepository;

    @Override
    public Void execute(CreateTaskCommand command, UUID projectId) {
        Project project = projectRepository.findProjectById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        project.createTask(command.user(), command.titleTask());
        projectRepository.updateProject(project);
        return null;
    }
}
