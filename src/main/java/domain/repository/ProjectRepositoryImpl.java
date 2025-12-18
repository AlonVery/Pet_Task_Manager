package domain.repository;

import domain.model.Project;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepositoryImpl {

    Optional<Project> findProjectById(UUID id);

    Project createEmptyProject(String name);

    boolean deleteProject(UUID id);

    void saveProject(Project project);

    void updateProject(Project project);

}
