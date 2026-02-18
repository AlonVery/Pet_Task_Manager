package application.usecase.project;

import application.command.project_command.GetAllProjectCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.model.Project;
import infra.db.jpa_entity.project.ProjectRepository;

import java.util.List;

public class GetAllProjectsUseCase implements UseCase<GetAllProjectCommand, List<Project>> {
    private final ProjectRepository projectRepository;

    public GetAllProjectsUseCase(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> execute(GetAllProjectCommand command) {
        System.out.println("GetAllProjectsUseCase");
        return projectRepository.getAllProjects();
    }
}
