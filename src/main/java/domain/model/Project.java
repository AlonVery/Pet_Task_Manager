package domain.model;

import domain.model.task.Task;
import domain.model.task.TaskFactory;
import domain.model.user.User;
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

    private TaskFactory taskFactory;

    private final List<Task> taskList = new ArrayList<>();

    public Project(String name, TaskFactory tf) {
        this.projectName = name;
        this.taskFactory = tf;
        this.projectId = UUID.randomUUID();
    }

    public Project(String name) {
        this.projectName = name;
        this.projectId = UUID.randomUUID();
    }

    public void createTask(User user, String title) {
        if (taskList.size() >= 100) {
            throw new IllegalStateException("In project cannot add more than 100 tasks");
        }
        Task task = taskFactory.createTask(user, title);
        if (!taskList.contains(task.getTaskId())) {
            Task taskToAdd = taskFactory.createTask(task.getTaskCreator(), task.getTaskTitle());

            Optional<Task> taskOptional = Optional.ofNullable(taskToAdd);
            taskOptional.ifPresent(taskList::add);
        } else {
            throw new IllegalStateException("Task already exists");
        }
    }

    public void startTask(UUID taskId) {
        hasTaskIdFromProject(taskId);
        Task task = findTask(taskId);
        task.startTask(task.getTaskCreator());
    }

    public void completeTask(UUID taskId) {
        hasTaskIdFromProject(taskId);
        Task task = findTask(taskId);
        task.completedTask(task.getTaskCreator());
    }

    public List<Task> getAllTasks() {
        return List.copyOf(taskList);
    }

    public void deleteTask(UUID taskId) {
        hasTaskIdFromProject(taskId);
        taskList.stream()
                .filter(task -> task.getTaskId().equals(taskId))
                .forEach(taskList::remove);
    }

    private boolean hasTaskIdFromProject(UUID taskId) {
        try {
            if (findTask(taskId) != null) {
                taskList.stream().filter(t -> t.getTaskId().equals(taskId)).findFirst().orElseThrow();
                return true;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Task findTask(UUID taskId) {
        return taskList.stream().filter(t -> t.getTaskId().equals(taskId)).findFirst().orElseThrow();
    }

    public Task updateUserFromTask(UUID taskId, User user) {
        Task task = findTask(taskId);
        task.setTaskCreator(user);
        task.setUpdatedAt(Instant.now());
        return task;
    }

    public Task updateTitleTask(UUID taskId, String title) {
        Task task = findTask(taskId);
        task.setTaskTitle(title);
        task.setUpdatedAt(Instant.now());
        return task;
    }

    @Override
    public String toString() {
        return "Project{" + "projectId=" + projectId + ", projectName='" + projectName + "\n" + ", taskList=" + taskList + "\n" + '}';
    }
}
