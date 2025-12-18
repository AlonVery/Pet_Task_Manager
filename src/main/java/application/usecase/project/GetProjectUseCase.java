package application.usecase.project;

import application.command.project_command.GetProjectCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.exception.ProjectNotFoundException;
import infra.db.jpa_entity.project.ProjectRepository;

public class GetProjectUseCase implements UseCase<GetProjectCommand, Void> {

    private final ProjectRepository projectRepository;

    public GetProjectUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Void execute(GetProjectCommand command) {
        projectRepository.findProjectById(command.projectId()).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        return null;
    }
}
