package application.dto.out.user;

import domain.model.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetAllUsersDTOResponse {
    private List<User> users;
}
