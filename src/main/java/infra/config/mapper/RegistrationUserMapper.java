package infra.config.mapper;

import application.command.registration.RegisterCommand;
import application.dto.in.user.RegisterUserDTORequest;
import application.dto.out.user.RegisterUserDTOResponse;
import domain.model.user.User;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@org.mapstruct.Mapper
public interface RegistrationUserMapper extends Mapper<
        RegisterUserDTORequest,
        RegisterCommand,
        String,
        RegisterUserDTOResponse
        > {
    RegistrationUserMapper instance = Mappers.getMapper(RegistrationUserMapper.class);

    @Override
    RegisterCommand toCommand(RegisterUserDTORequest dto);

    @Override
    @Mapping(target = "message", constant = "User register successfully")
    RegisterUserDTOResponse toResponse(String result);
}