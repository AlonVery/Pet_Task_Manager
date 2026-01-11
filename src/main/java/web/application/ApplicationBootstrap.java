package web.application;

import application.usecase.registration.GetAllUsersUseCase;
import application.usecase.registration.UserRegistrationUseCase;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;
import infra.db.in_memory_repository.InMemoryUserRepository;
import infra.security.PasswordEncoderSha256;
import web.adapter.SunHttpSeverAdapter;
import web.application.config.ApplicationConfig;
import web.controller.controllers.GetAllUsersController;
import web.controller.controllers.RegistrationUserController;
import web.handler.GetAllUsersHandler;
import web.handler.RegistrationUserHandler;
import web.router.Router;
import web.server.SunHttpServerLocal;

public class ApplicationBootstrap {
    public void start(ApplicationConfig conf) {

        UserRepository userRepository = new InMemoryUserRepository();
        //ProjectRepository projectRepository = new ProjectRepository(); //#todo: add project logic handler on response dir

        PasswordEncoder encoder = new PasswordEncoderSha256();

        //UseCases
        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(userRepository, encoder);
        //UserLoginUseCase userLoginUseCase = new UserLoginUseCase(userRepository, encoder); //#todo: add login controller
        GetAllUsersUseCase getAllUsersUseCase = new GetAllUsersUseCase(userRepository);

        //Controllers
        RegistrationUserController registrationUserController = new RegistrationUserController(userRegistrationUseCase);
        GetAllUsersController getAllUsersController = new GetAllUsersController(getAllUsersUseCase);

        //Handlers
        RegistrationUserHandler registerHandler = new RegistrationUserHandler(registrationUserController);
        GetAllUsersHandler getAllUsersHandler = new GetAllUsersHandler(getAllUsersController);

        // 5. Router
        Router router = new Router();
        router.addRoute("POST", "/users/registration", registerHandler);
        router.addRoute("GET", "/users", getAllUsersHandler);

        // 6. Adapter
        SunHttpSeverAdapter adapter =
                new SunHttpSeverAdapter(router);

        // 7. Server
        SunHttpServerLocal server =
                new SunHttpServerLocal(conf.httpPort(),adapter);

        server.start();
    }
}
