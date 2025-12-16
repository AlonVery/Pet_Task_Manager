package infra.db.jpa_entity.project;

import domain.exception.ProjectNotFoundException;
import domain.model.Project;
import domain.model.task.TaskFactory;
import domain.repository.ProjectRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectRepository implements ProjectRepositoryImpl {
    private final ConcurrentHashMap<UUID, Project> localProjectDB = new ConcurrentHashMap<>();
    private final TaskFactory taskFactory = new TaskFactory();

    @Override
    public Optional<Project> findProjectById(UUID projectId) {
        return localProjectDB.values().stream().filter(p -> p.getProjectId().equals(projectId)).findFirst();
    }

    @Override
    public Project createEmptyProject(String projectName) {
        Project prjct = new Project(projectName);
        localProjectDB.put(prjct.getProjectId(), prjct);
        return prjct;
    }

    @Override
    public boolean deleteProject(UUID projectId) {
        if (localProjectDB.containsKey(projectId)) {
            localProjectDB.remove(findProjectById(projectId));
            return true;
        }
        throw new ProjectNotFoundException(projectId);
    }

    public List<Project> getAllProjects() {
        return List.copyOf(localProjectDB.values());
    }

}
