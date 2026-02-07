package infra.db.jpa_entity.project;

import domain.exception.ProjectNotFoundException;
import domain.model.Project;
import domain.model.task.TaskFactory;
import domain.repository.ProjectRepositoryImpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectRepository implements  ProjectRepositoryImpl {
    private final ConcurrentHashMap<UUID, Project> localProjectDB = new ConcurrentHashMap<>();
    private final TaskFactory taskFactory = new TaskFactory();

    @Override
    public Optional<Project> findProjectById(UUID projectId) {
        return Optional.ofNullable(localProjectDB.get(projectId));
    }

    @Override
    public Project  createEmptyProject(String projectName) {
        return new Project(projectName, taskFactory);
    }

    @Override
    public boolean deleteProject(UUID projectId) {
        if (localProjectDB.containsKey(projectId)) {
            localProjectDB.remove(projectId);
            return true;
        }
        throw new ProjectNotFoundException(projectId);
    }

    @Override
    public void saveProject(Project project) {
        localProjectDB.put(project.getProjectId(), project);
        System.out.println("Saving project " + project.getProjectId());
    }

    @Override
    public void updateProject(Project project) {
        localProjectDB.replace(project.getProjectId(), project);
    }

    public List<Project> getAllProjects() {
        return List.copyOf(localProjectDB.values());
    }

    @Override
    public String toString() {
        return "ProjectRepository{" +
                "localProjectDB=" + localProjectDB +
                ", taskFactory=" + taskFactory +
                '}';
    }
}
