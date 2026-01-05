package application.usecase.registration;

import application.command.registration.RegisterCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.model.user.User;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;
import domain.service.AuthService;

public class UserRegistrationUseCase implements UseCase<RegisterCommand, User> {

    private final AuthService authService;
    private final Object lock = new Object();

    public UserRegistrationUseCase(UserRepository userRepository, PasswordEncoder encoder) {
        this.authService = new AuthService(userRepository, encoder);
    }

    @Override
    public User execute(RegisterCommand command) {
        synchronized (lock) {
           return authService.register(command.userName(), command.email(), command.password());
        }
    }
}
