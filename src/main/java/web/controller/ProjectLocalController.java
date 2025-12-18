package web.controller;

import application.command.project_command.CreateProjectCommand;
import application.command.project_command.DeleteProjectCommand;
import application.command.project_command.GetProjectCommand;
import application.command.task.CompleteTaskCommand;
import application.command.task.CreateTaskCommand;
import application.command.task.DeleteTaskCommand;
import application.command.task.StartTaskCommand;
import domain.model.user.User;
import infra.dispatcher.UseCaseDispatcher;

import java.util.UUID;

public class ProjectLocalController {

    private final UseCaseDispatcher dispatcher;

    public ProjectLocalController(UseCaseDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void createProject(String nameProject) {
        dispatcher.dispatch(new CreateProjectCommand(nameProject));
    }

    public void deleteProject(UUID projectId) {
        dispatcher.dispatch(new DeleteProjectCommand(projectId));
    }

    public void getProject(UUID projectId) {
        dispatcher.dispatch(new GetProjectCommand(projectId));
    }

    public void createTask(String projectName, User user, String titleTask) {
        dispatcher.dispatch(new CreateTaskCommand(projectName, user, titleTask));
    }

    public void startTask(UUID taskId, User user) {
        dispatcher.dispatch(new StartTaskCommand(taskId, user));
    }

    public void completeTask(UUID taskId, User user) {
        dispatcher.dispatch(new CompleteTaskCommand(taskId, user));
    }

    public void deleteTask(UUID taskId, User user) {
        dispatcher.dispatch(new DeleteTaskCommand(taskId, user));
    }

}
