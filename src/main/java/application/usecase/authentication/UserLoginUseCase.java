package application.usecase.authentication;

import application.command.authentication.LoginCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;
import domain.service.AuthService;

public class UserLoginUseCase implements UseCase<LoginCommand, String> {
    
    private final AuthService authService;

    public UserLoginUseCase(UserRepository userRepository, PasswordEncoder encoder) {
        System.out.println("USERS AT LOGIN: " + userRepository.getAllUsers());
        this.authService = new AuthService(userRepository, encoder);
    }

    @Override
    public String execute(LoginCommand command) {
        System.out.println(authService.login(command.username(), command.password()));
        System.out.println("LOGIN ATTEMPT: '" + command.username() + "'");
        return authService.login(command.username(), command.password());
    }
}
