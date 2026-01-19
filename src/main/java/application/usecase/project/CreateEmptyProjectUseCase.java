package application.usecase.project;

import application.command.project_command.CreateEmptyProjectCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.exception.UserNotFoundException;
import domain.model.Project;
import domain.repository.UserRepository;
import infra.db.jpa_entity.project.ProjectRepository;

import java.util.UUID;

public class CreateEmptyProjectUseCase implements UseCase<CreateEmptyProjectCommand, UUID> {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public CreateEmptyProjectUseCase(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UUID execute(CreateEmptyProjectCommand command) {
        if (!userRepository.existsByEmail(command.email())) {
            throw new UserNotFoundException("User not found: " + command.email());
        }
        Project project = projectRepository.createEmptyProject(command.projectName());
        projectRepository.saveProject(project);
        return project.getProjectId();
    }
}
