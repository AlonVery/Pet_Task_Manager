package application.usecase.project;

import application.usecase.usecaseimpl.UseCase;
import application.command.project_command.CreateProjectCommand;
import domain.model.Project;
import infra.db.jpa_entity.project.ProjectRepository;

public class CreateProjectUseCase implements UseCase<CreateProjectCommand, Void> {

    private ProjectRepository projectRepository;

    @Override
    public Void execute(CreateProjectCommand command) {
        Project project = projectRepository.createEmptyProject(command.projectName());
        projectRepository.saveProject(project);
        return null;
    }
}
