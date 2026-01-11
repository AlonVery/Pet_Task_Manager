package infra.config.mapper;

import application.command.registration.GetAllUsersCommand;
import application.dto.in.user.GetAllUsersDTORequest;
import application.dto.out.user.GetAllUsersDTOResponse;
import domain.model.user.User;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@org.mapstruct.Mapper
public interface GetAllUsersMapper extends Mapper<
        GetAllUsersDTORequest,
        GetAllUsersCommand,
        List<User>,
        GetAllUsersDTOResponse
        > {
    GetAllUsersMapper INSTANCE = Mappers.getMapper(GetAllUsersMapper.class);

    @Override
    GetAllUsersCommand toCommand(GetAllUsersDTORequest dto);


    @Override
    default GetAllUsersDTOResponse toResponse(List<User> result) {
        GetAllUsersDTOResponse resp = new GetAllUsersDTOResponse();
        resp.setUsers(result);
        return resp;
    }
}
