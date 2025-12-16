package domain.repository;

import domain.model.Project;
import domain.model.task.Task;
import domain.model.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepositoryImpl {

    Optional<Project> findProjectById(UUID id);

    void createEmptyProject(String name, Project project);

    void deleteProject(UUID id);

    Project updateTaskUserFromProject(UUID projectId, UUID taskId, User user);

    Project updateTaskTitleFromProject(UUID projectId, UUID taskId, String title);

    void deleteTaskFromProject(UUID projectId, UUID taskId);

    List<Task> getAllTasksFromProject(UUID projectId);

    Task getTaskFromProject(UUID projectId, UUID taskId);

    List<Task> createTask(UUID projectId, UUID taskId, User user, String title);

    Task updateTitleTask(UUID projectId, UUID taskId, Task task, String title);

    void startTask(UUID projectId, UUID taskId);

    void completeTask(UUID projectId, UUID taskId);

    void deleteTask(UUID projectId, UUID taskId);

}
