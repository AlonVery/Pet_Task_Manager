package domain.service;

import domain.exception.ProjectEmptyException;
import domain.exception.ProjectNotFoundException;
import domain.model.Project;
import domain.model.user.User;
import infra.db.jpa_entity.project.ProjectRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project createEmptyProject(String name) {
        if (name.isEmpty()) {
            throw new ProjectEmptyException(name);
        }
        return projectRepository.createEmptyProject(name);
    }

    public boolean deleteProject(UUID projectId) {
        return projectRepository.deleteProject(projectId);
    }

    public Project getProjectById(UUID projectId) {
        return projectRepository.findProjectById(projectId).
                orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    public List<Project> getAllProjects() {
        return projectRepository.getAllProjects();
    }


}
