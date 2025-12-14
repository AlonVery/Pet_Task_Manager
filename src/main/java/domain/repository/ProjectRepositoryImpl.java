package domain.repository;

import domain.model.Project;
import domain.model.user.User;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepositoryImpl {

    Optional<Project> findProjectById(UUID id);

    void createProject(String name, Project project);

    void deleteProject(UUID id);

    void updateTaskUserFromProject(UUID projectId, UUID taskId, User user);

    void updateTaskTitleFromProject(UUID projectId, UUID taskId, String title);

    void deleteTaskFromProject(UUID projectId, UUID taskId);

}
