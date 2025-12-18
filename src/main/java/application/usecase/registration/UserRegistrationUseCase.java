package application.usecase.registration;

import application.command.registration.RegisterCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepositoryImpl;
import domain.service.AuthService;
import domain.service.UserService;
import infra.db.in_memory_repository.InMemoryUserRepository;

public class UserRegistrationUseCase implements UseCase<RegisterCommand, Void> {

    private UserRepositoryImpl userRepository;
    private UserService userService;
    private PasswordEncoder encoder;
    private final AuthService authService = new AuthService(new InMemoryUserRepository(),
            new UserService(userRepository), encoder);

    @Override
    public Void execute(RegisterCommand command) {
        authService.register(
                command.userName(),
                command.email(),
                command.password());
        return null;
    }
}
