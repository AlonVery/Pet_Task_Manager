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
        //todo:
        /*
        * List<User> execute(GetAllUsersCommand command){
        * if(command,query().isEmpty()){
        * return userRepository.getAllUsers();
        * }
        * return userRepository.getUsersByQuery(command.query()); <- Тут передаю в репозиторий мапу с квери, чтоб уже в репозитории раскрыть и найти то, что нужно
        * */
        return userRepository.getAllUsers();
    }
}
