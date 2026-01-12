package application.usecase.registration;

import application.command.registration.RegisterCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.exception.UserAlreadyExistException;
import domain.model.user.User;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;
import domain.service.AuthService;

public class UserRegistrationUseCase implements UseCase<RegisterCommand, String> {

    private final AuthService authService;
    private final Object lock = new Object();

    public UserRegistrationUseCase(UserRepository userRepository, PasswordEncoder encoder) {
        this.authService = new AuthService(userRepository, encoder);
    }

    @Override
    public String execute(RegisterCommand command) {
        synchronized (lock) {
            try {
                authService.register(command.userName(), command.email(), command.password());
            } catch (RuntimeException e) {
                throw new UserAlreadyExistException("Email already in use");
            }
        }
        return "User registration successfully";
    }
}
