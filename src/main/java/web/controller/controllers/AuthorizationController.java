package web.controller.controllers;

import application.command.authentication.LoginCommand;
import application.dto.in.user.AuthDTORequest;
import application.dto.out.user.AuthDTOResponse;
import application.usecase.usecaseimpl.UseCase;
import infra.mapper.AuthorizationUserMapper;

public class AuthorizationController  extends BaseController<
        AuthDTORequest,
        LoginCommand,
        String,
        AuthDTOResponse
        > {
    public AuthorizationController(UseCase<LoginCommand, String> useCase) {
        super(useCase, AuthorizationUserMapper.INSTANCE);
    }

    @Override
    protected Class<AuthDTORequest> getRequestClass() {
        return AuthDTORequest.class;
    }
}
