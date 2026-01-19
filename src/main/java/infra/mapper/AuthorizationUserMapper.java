package infra.mapper;

import application.command.authentication.LoginCommand;
import application.dto.in.user.AuthDTORequest;
import application.dto.out.user.AuthDTOResponse;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper
public interface AuthorizationUserMapper extends Mapper<
        AuthDTORequest,
        LoginCommand,
        String,
        AuthDTOResponse
        > {
    AuthorizationUserMapper INSTANCE = Mappers.getMapper(AuthorizationUserMapper.class);

    @Override
    LoginCommand toCommand(AuthDTORequest authDTORequest);

    @Override
    @Mapping(target = "isSuccess")
    AuthDTOResponse toResponse(String isSuccess);
}
