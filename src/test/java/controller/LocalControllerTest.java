package controller;

import application.command.authentication.LoginCommand;
import application.command.project_command.CreateProjectCommand;
import application.command.project_command.GetAllProjectCommand;
import application.command.project_command.GetProjectCommand;
import application.command.registration.GetAllUsersCommand;
import application.command.registration.RegisterCommand;
import application.command.task.CompleteTaskCommand;
import application.command.task.CreateTaskCommand;
import application.command.task.DeleteTaskCommand;
import application.command.task.StartTaskCommand;
import application.usecase.authentication.UserLoginUseCase;
import application.usecase.project.*;
import application.usecase.registration.GetAllUsersUseCase;
import application.usecase.registration.UserRegistrationUseCase;
import domain.model.Project;
import domain.model.user.User;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;
import infra.db.in_memory_repository.InMemoryUserRepository;
import infra.db.jpa_entity.project.ProjectRepository;
import infra.dispatcher.UseCaseDispatcher;
import infra.security.PasswordEncoderSha256;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import web.controller.ProjectLocalController;

import java.util.List;
import java.util.Map;

public class LocalControllerTest {

    ProjectLocalController controller;
    UseCaseDispatcher dispatcher;


    @BeforeEach
    void setUp() {
        final UserRepository userRepository = new InMemoryUserRepository();
        final PasswordEncoder encoder = new PasswordEncoderSha256();
        ProjectRepository projectRepository = new ProjectRepository();

        StartTaskUseCase startTaskUseCase = new StartTaskUseCase();
        CompleteTaskUseCase completeTaskUseCase = new CompleteTaskUseCase();
        DeleteTaskUseCase deleteTaskUseCase = new DeleteTaskUseCase();
        CreateProjectUseCase createProjectUseCase = new CreateProjectUseCase(projectRepository);
        GetProjectUseCase getProjectUseCase = new GetProjectUseCase(projectRepository);
        UserRegistrationUseCase userRegistration = new UserRegistrationUseCase(userRepository, encoder);
        UserLoginUseCase userLogin = new UserLoginUseCase(userRepository, encoder);
        AddTaskToProjectUseCase createTaskUseCase = new AddTaskToProjectUseCase(projectRepository);
        GetAllProjectsUseCase getAllProjectsUseCase = new GetAllProjectsUseCase(projectRepository);
        GetAllUsersUseCase getAllUsersUseCase = new GetAllUsersUseCase(userRepository);

        dispatcher = UseCaseDispatcher.getInstance((Map) Map.of(
                StartTaskCommand.class, startTaskUseCase,
                CreateTaskCommand.class, createTaskUseCase,
                GetAllProjectCommand.class, getAllProjectsUseCase,
                CompleteTaskCommand.class, completeTaskUseCase,
                DeleteTaskCommand.class, deleteTaskUseCase,
                CreateProjectCommand.class, createProjectUseCase,
                GetProjectCommand.class, getProjectUseCase,
                LoginCommand.class, userLogin,
                RegisterCommand.class, userRegistration,
                GetAllUsersCommand.class, getAllUsersUseCase
        ));
        controller = new ProjectLocalController(dispatcher);
    }

    @Test
    public void mainTest() {
        controller.createProject("Test Project_1");
        controller.createProject("Test Project_2");
        controller.createProject("Test Project_3");
        controller.createProject("Test Project_4");
        List<Project> list = controller.getAllProjects(); //#debug
        list.forEach(System.out::println);
        Assertions.assertEquals(4, list.size());
    }

    @Test
    public void registerUserTest() {
        controller.registerUser("Alex", "Qwerty@erwe.er", "1234");
        controller.registerUser("Meg", "Q2werty@erwe.er", "1234");
        controller.registerUser("Max", "2@erwe.er", "1234");
        List<User> list = controller.getAllUsers();
        list.forEach(System.out::println);
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertEquals(4, list.size());
    }

    @Test
    public void loginUserTest() {
        controller.registerUser("Alex", "Qwe@r.ty", "1234");
        Assertions.assertTrue(controller.loginUser("Alex", "1234"));
    }
}
