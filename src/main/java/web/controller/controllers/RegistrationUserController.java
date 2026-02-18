package web.controller.controllers;

import application.command.registration.RegisterCommand;
import application.dto.in.user.RegisterUserDTORequest;
import application.dto.out.user.RegisterUserDTOResponse;
import application.usecase.usecaseimpl.UseCase;
import infra.mapper.RegistrationUserMapper;

public class RegistrationUserController extends BaseController<
        RegisterUserDTORequest,
        RegisterCommand,
        String,
        RegisterUserDTOResponse
        > {
    public RegistrationUserController(UseCase<RegisterCommand, String> useCase) {
        super(useCase, RegistrationUserMapper.instance);
    }

    @Override
    protected Class<RegisterUserDTORequest> getRequestClass() {
        return RegisterUserDTORequest.class;
    }
}
