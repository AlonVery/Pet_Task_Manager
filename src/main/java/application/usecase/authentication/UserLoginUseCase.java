package application.usecase.authentication;

import application.command.authentication.LoginCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepositoryImpl;
import domain.service.AuthService;
import domain.service.UserService;
import infra.db.in_memory_repository.InMemoryUserRepository;

public class UserLoginUseCase implements UseCase<LoginCommand, Void> {

    private UserRepositoryImpl userRepository;
    private PasswordEncoder encoder;
    private final AuthService authService = new AuthService(userRepository, encoder);

    @Override
    public Void execute(LoginCommand command) {
        authService.login(command.username(), command.password());
        return null;
    }
}
