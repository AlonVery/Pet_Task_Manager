package web.application;

import application.usecase.authentication.UserLoginUseCase;
import application.usecase.project.CreateEmptyProjectUseCase;
import application.usecase.registration.GetAllUsersUseCase;
import application.usecase.registration.UserRegistrationUseCase;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;
import infra.db.jpa_entity.JdbcUserRepository;
import infra.db.jpa_entity.project.ProjectRepository;
import infra.security.PasswordEncoderSha256;
import web.adapter.SunHttpSeverAdapter;
import web.application.config.ApplicationConfig;
import web.controller.controllers.AuthorizationController;
import web.controller.controllers.GetAllUsersController;
import web.controller.controllers.RegistrationUserController;
import web.controller.controllers.project.CreateEmptyProjectController;
import web.handler.AuthorizationHandler;
import web.handler.GetAllUsersHandler;
import web.handler.RegistrationUserHandler;
import web.handler.project.CreateEmptyProjectControllerHandler;
import web.router.Router;
import web.server.SunHttpServerLocal;


public class ApplicationBootstrap {
    public void start(ApplicationConfig conf) {

        // UserRepository userRepository = new InMemoryUserRepository();
        UserRepository userRepository = new JdbcUserRepository();
        ProjectRepository projectRepository = new ProjectRepository(); //#todo: add project logic handler on response dir

        PasswordEncoder encoder = new PasswordEncoderSha256();

        //UseCases
        //authorization:
        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(userRepository, encoder);
        UserLoginUseCase userLoginUseCase = new UserLoginUseCase(userRepository, encoder);
        GetAllUsersUseCase getAllUsersUseCase = new GetAllUsersUseCase(userRepository);
        //project logic:
        CreateEmptyProjectUseCase createEmptyProjectUseCase = new CreateEmptyProjectUseCase(projectRepository, userRepository);

        //Controllers
        RegistrationUserController registrationUserController = new RegistrationUserController(userRegistrationUseCase);
        AuthorizationController authorizationController = new AuthorizationController(userLoginUseCase);
        GetAllUsersController getAllUsersController = new GetAllUsersController(getAllUsersUseCase);
        CreateEmptyProjectController createEmptyProjectController = new CreateEmptyProjectController(createEmptyProjectUseCase);

        //Handlers
        var registerHandler = new RegistrationUserHandler(registrationUserController);
        var authorizationHandler = new AuthorizationHandler(authorizationController);
        var getAllUsersHandler = new GetAllUsersHandler(getAllUsersController);
        var createEmptyProjectControllerHandler = new CreateEmptyProjectControllerHandler(createEmptyProjectController);

        //Router
        Router router = new Router();
        router.addRoute("POST", "/users/registration", registerHandler);
        router.addRoute("GET", "/users", getAllUsersHandler);
        router.addRoute("POST", "/users/login", authorizationHandler);
        router.addRoute("POST", "/projects/create", createEmptyProjectControllerHandler);

        //Adapter
        SunHttpSeverAdapter adapter = new SunHttpSeverAdapter(router);

        //Server
        SunHttpServerLocal server = new SunHttpServerLocal(conf.httpPort(), adapter);

        server.start();
    }
}
