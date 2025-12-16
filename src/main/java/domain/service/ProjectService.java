package domain.service;

import domain.model.Project;
import domain.model.user.User;
import infra.db.jpa_entity.project.ProjectRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public void createProject(String name, Project project) {
        projectRepository.createEmptyProject(name, project);
        System.out.println("Empty project " + name + " created.");
    }

    public void deleteProject(UUID projectId) {
        projectRepository.deleteProject(projectId);
    }

    public Project getProjectById(UUID projectId) {
        return projectRepository.findProjectById(projectId).
                orElseThrow(() -> new RuntimeException("Project with: " + projectId + " - not found."));
    }

    public Project updateUserFromTaskProject(UUID projectId, UUID taskId, User user) {
        return projectRepository.updateTaskUserFromProject(projectId, taskId, user);
    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }


}
