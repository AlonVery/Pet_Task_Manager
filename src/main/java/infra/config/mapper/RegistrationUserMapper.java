package infra.config.mapper;

import application.command.registration.RegisterCommand;
import application.dto.in.user.RegisterUserDTORequest;
import application.dto.out.user.RegisterUserDTOResponse;
import org.mapstruct.factory.Mappers;


@org.mapstruct.Mapper
public interface RegistrationUserMapper extends Mapper<RegisterUserDTORequest, RegisterCommand, RegisterUserDTOResponse>{
    RegistrationUserMapper instance =  Mappers.getMapper(RegistrationUserMapper.class);

    @Override
    RegisterCommand toCommand(RegisterUserDTORequest dto);

    @Override
    default RegisterUserDTOResponse toResponse(Object o){
        return new  RegisterUserDTOResponse("User register successfully");
    }

}
