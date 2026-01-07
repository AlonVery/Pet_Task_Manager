package web.application;

import application.usecase.registration.UserRegistrationUseCase;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;
import infra.db.in_memory_repository.InMemoryUserRepository;
import infra.security.PasswordEncoderSha256;
import web.application.config.ApplicationConfig;
import web.controller.controllers.RegistrationUserController;
import web.routing.SunHttpSeverAdapter;

public class ApplicationBootstrap {
    public Application start(ApplicationConfig conf) {

        UserRepository userRepository = new InMemoryUserRepository();
        //ProjectRepository projectRepository = new ProjectRepository(); //#todo: add project logic handler on response dir

        PasswordEncoder encoder = new PasswordEncoderSha256();

        //UseCases
        UserRegistrationUseCase userRegistrationUseCase = new UserRegistrationUseCase(userRepository, encoder);
        //UserLoginUseCase userLoginUseCase = new UserLoginUseCase(userRepository, encoder); //#todo: add login controller

        //Controllers
        RegistrationUserController registrationUserController = new RegistrationUserController(userRegistrationUseCase);

        SunHttpSeverAdapter server = new SunHttpSeverAdapter();

        server.start(conf.httpPort(), registrationUserController);

        return new RunningApplication(server);
    }
}
