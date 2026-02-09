package application.usecase.authentication;

import application.command.authentication.LoginCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.repository.PasswordEncoder;
import domain.repository.UserRepository;
import domain.service.AuthService;

public class UserLoginUseCase implements UseCase<LoginCommand, String> {
    
    private final AuthService authService;

    public UserLoginUseCase(UserRepository userRepository, PasswordEncoder encoder) {
        this.authService = new AuthService(userRepository, encoder);
    }

    @Override
    public String execute(LoginCommand command) {
        return authService.login(command.email(), command.password());
    }
}
