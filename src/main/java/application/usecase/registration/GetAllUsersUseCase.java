package application.usecase.registration;

import application.command.registration.GetAllUsersCommand;
import application.usecase.usecaseimpl.UseCase;
import domain.model.user.User;
import domain.repository.UserRepository;

import java.util.List;

public class GetAllUsersUseCase implements UseCase<GetAllUsersCommand, List<User>> {

    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> execute(GetAllUsersCommand command) {
        return userRepository.getAllUsers();
    }
}
