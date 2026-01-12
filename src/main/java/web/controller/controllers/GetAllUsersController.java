package web.controller.controllers;

import application.command.registration.GetAllUsersCommand;
import application.dto.in.user.GetAllUsersDTORequest;
import application.dto.out.user.GetAllUsersDTOResponse;
import application.usecase.usecaseimpl.UseCase;
import domain.model.user.User;

import static infra.mapper.GetAllUsersMapper.*;

public class GetAllUsersController extends BaseController<
        GetAllUsersDTORequest,
        GetAllUsersCommand,
        java.util.List<User>,
        GetAllUsersDTOResponse
        > {
    public GetAllUsersController(UseCase<GetAllUsersCommand, java.util.List<User>> useCase) {
        super(useCase, INSTANCE);
    }

    @Override
    protected Class<GetAllUsersDTORequest> getRequestClass() {
        return GetAllUsersDTORequest.class;
    }
}
