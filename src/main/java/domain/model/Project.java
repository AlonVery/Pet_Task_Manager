package domain.model;

import domain.model.task.Task;
import domain.model.task.TaskFactory;
import domain.model.user.User;
import domain.model.user.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.*;

@EqualsAndHashCode(of = "projectId")
public class Project {
    @Getter
    private final UUID projectId;
    @Getter
    private final String projectName;

    private final TaskFactory taskFactory;

    private static final int MAX_TASKS = 100;

    private final List<Task> taskList = new ArrayList<>();

    public Project(String name, TaskFactory taskFactory) {
        this.projectName = name;
        this.taskFactory = Objects.requireNonNull(taskFactory);
        this.projectId = UUID.randomUUID();
    }

    public void createTask(User user, String title) {
        if (taskList.size() >= MAX_TASKS) {
            throw new IllegalStateException("In project cannot add more than 100 tasks");
        }
        Task task = Objects.requireNonNull(taskFactory.createTask(user, title), "TaskFactory returned null");
        taskList.add(task);
    }

    public void startTask(UUID taskId, User user) {
        hasTaskIdFromProject(taskId);
        Task task = findTask(taskId);
        task.startTask(user);
    }

    public void completeTask(UUID taskId, User user) {
        hasTaskIdFromProject(taskId);
        Task task = findTask(taskId);
        task.completedTask(user);
    }

    public List<Task> getAllTasks() {
        return List.copyOf(taskList);
    }

    public void deleteTask(UUID taskId, User user) {
        hasTaskIdFromProject(taskId);
        if (user.getUserRole() == UserRole.MANAGER || user.getUserRole() == UserRole.USER) {
            boolean removed = taskList.removeIf(t -> t.getTaskId().equals(taskId));
            if (!removed) {
                throw new IllegalStateException("Task with id " + taskId + " not found");
            }
            return;
        }
        throw new IllegalStateException("User cannot delete task, user role " + user.getUserRole());
    }

    private void hasTaskIdFromProject(UUID taskId) {
        taskList.stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task not found in project"));
    }

    public Task findTask(UUID taskId) {
        return taskList.stream().filter(t -> t.getTaskId().equals(taskId)).findFirst().orElseThrow();
    }

    public boolean existsTask(UUID taskId) {
        return taskList.stream()
                .anyMatch(t -> t.getTaskId().equals(taskId));
    }

    public Task updateUserFromTask(UUID taskId, User user) {
        Task task = findTask(taskId);
        task.changeCreator(user);
        return task;
    }

    public Task updateTitleTask(UUID taskId, String title) {
        Task task = findTask(taskId);
        task.changeTitle(title);
        return task;
    }

    //#debug
    @Override
    public String toString() {
        return "Project{" + "projectId=" + projectId + ", projectName='" + projectName + "`, taskList=" + taskList + '}';
    }
}
