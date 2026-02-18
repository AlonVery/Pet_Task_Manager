package web.controller;

import application.command.authentication.LoginCommand;
import application.command.project_command.CreateEmptyProjectCommand;
import application.command.project_command.DeleteProjectCommand;
import application.command.project_command.GetAllProjectCommand;
import application.command.project_command.GetProjectCommand;
import application.command.registration.GetAllUsersCommand;
import application.command.registration.RegisterCommand;
import application.command.task.CompleteTaskCommand;
import application.command.task.CreateTaskCommand;
import application.command.task.DeleteTaskCommand;
import application.command.task.StartTaskCommand;
import domain.model.Project;
import domain.model.user.User;
import infra.dispatcher.UseCaseDispatcher;

import java.util.List;
import java.util.UUID;

public class ProjectLocalController {

    private final UseCaseDispatcher dispatcher;

    public ProjectLocalController(UseCaseDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void createProject(String email, String projectName) {
        dispatcher.dispatch(new CreateEmptyProjectCommand(email, projectName));
    }

    public void deleteProject(UUID projectId) {
        dispatcher.dispatch(new DeleteProjectCommand(projectId));
    }

    public void getProject(UUID projectId) {
        dispatcher.dispatch(new GetProjectCommand(projectId));
    }

    public List<Project> getAllProjects() {
        return dispatcher.dispatch(new GetAllProjectCommand());
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

    public void registerUser(String userName, String email, String password) {
        dispatcher.dispatch(new RegisterCommand(userName, email, password));
    }

    public boolean loginUser(String username, String password) {
        return dispatcher.dispatch(new LoginCommand(username, password));
    }

    public List<User> getAllUsers() {
        return dispatcher.dispatch(new GetAllUsersCommand());
    }

}
