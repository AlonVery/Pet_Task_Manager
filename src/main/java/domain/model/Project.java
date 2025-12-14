package domain.model;

import domain.model.task.Task;
import domain.model.task.TaskFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.*;

@EqualsAndHashCode(of = "projectId")
public class Project {
    @Getter
    private final UUID projectId;
    @Getter
    private final String projectName;

    private final TaskFactory taskFactory;

    private final List<Task> taskList = new ArrayList<>();

    public Project(String name, TaskFactory tf) {
        this.projectName = name;
        this.taskFactory = tf;
        this.projectId = UUID.randomUUID();
    }

    public void addTask(Task task) {
        if (taskList.size() >= 100) {
            throw new IllegalStateException("In project cannot add more than 100 tasks");
        }
        if (taskList.contains(task.getTaskId())) {
            throw new IllegalStateException("Task already exists");
        }
        Task taskToAdd = taskFactory.createTask(task.getTaskCreator(), task.getTaskTitle());

        Optional<Task> taskOptional = Optional.ofNullable(taskToAdd);
        taskOptional.ifPresent(taskList::add);
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

    private void hasTaskIdFromProject(UUID taskId) {
        try {
            if (findTask(taskId) != null) {
                taskList.stream().filter(t -> t.getTaskId().equals(taskId)).findFirst().orElseThrow();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Task findTask(UUID taskId) {
        return taskList.stream().filter(t -> t.getTaskId().equals(taskId)).findFirst().orElseThrow();
    }

    @Override
    public String toString() {
        return "Project{" + "projectId=" + projectId + ", projectName='" + projectName + "\n" + ", taskList=" + taskList + "\n" + '}';
    }
}
