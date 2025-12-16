package infra.db.jpa_entity.project;

import domain.model.Project;
import domain.model.user.User;
import domain.model.task.Task;
import domain.model.task.TaskFactory;
import domain.repository.ProjectRepositoryImpl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProjectRepository implements ProjectRepositoryImpl {
    private final ConcurrentHashMap<UUID, Project> localProjectDB= new ConcurrentHashMap<>();
    private final TaskFactory taskFactory = new TaskFactory();

    @Override
    public Optional<Project> findProjectById(UUID id) {
        return localProjectDB.values().stream().filter(p -> p.getProjectId().equals(id)).findFirst();
    }

    @Override
    public void createEmptyProject(String name, Project project) {
        if(localProjectDB.containsKey(project.getProjectId())) {
            throw new IllegalStateException("Project already exists");
        }
        Project prjct = new Project(name, taskFactory);
        localProjectDB.put(prjct.getProjectId(), project);
    }

    @Override
    public void deleteProject(UUID id) {
        localProjectDB.remove(findProjectById(id));
    }

    @Override
    public Project updateTaskUserFromProject(UUID projectId, UUID taskId, User user) {
        Project prjct = localProjectDB.get(findProjectById(projectId));
        Task task = prjct.findTask(taskId);
        task.setTaskCreator(user);
        task.setUpdatedAt(Instant.now());
        return prjct;
    }

    @Override
    public Project updateTaskTitleFromProject(UUID projectId, UUID taskId, String title) {
        Project prjct = localProjectDB.get(findProjectById(projectId));
        Task task = prjct.findTask(taskId);
        task.setTaskTitle(title);
        task.setUpdatedAt(Instant.now());
        return prjct;
    }

    @Override
    public void deleteTaskFromProject(UUID projectId, UUID taskId) {
        Project prjct = localProjectDB.get(findProjectById(projectId));
        prjct.deleteTask(taskId);
    }

    @Override
    public List<Task> getAllTasksFromProject(UUID projectId) {
        Project project = localProjectDB.get(projectId);
         return project.getAllTasks();
    }

    @Override
    public Task getTaskFromProject(UUID projectId, UUID taskId) {
        Project project = localProjectDB.get(projectId);
        return project.findTask(taskId);
    }

    @Override
    public List<Task> createTask(UUID projectId, UUID taskId, User user,  String title) {
        Project project = localProjectDB.get(projectId);
        project.addTask(taskFactory.createTask(user, title));
        return project.getAllTasks();
    }

    @Override
    public void deleteTask(UUID projectId, UUID taskId) {

    }

    @Override
    public Task updateTitleTask(UUID projectId, UUID taskId, Task task, String title) {
        return null;
    }

    @Override
    public void startTask(UUID projectId, UUID taskId) {

    }

    @Override
    public void completeTask(UUID projectId, UUID taskId) {

    }

    public List<Project> getAllProjects(){
        return List.copyOf(localProjectDB.values());
    }

    private Task findTaskById(UUID taskId, Project project){
        if(!localProjectDB.containsValue(project)){throw new IllegalArgumentException("Project not found");}
        if(!localProjectDB.containsKey(taskId)){throw new IllegalArgumentException("Task not found");}
        return project.findTask(taskId);
    }
}
