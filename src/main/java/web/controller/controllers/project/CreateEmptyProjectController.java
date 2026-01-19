package web.controller.controllers.project;

import application.command.project_command.CreateEmptyProjectCommand;
import application.dto.in.project.CreateEmptyProjectDTORequest;
import application.dto.out.project.CreateEmptyProjectDTOResponse;
import application.usecase.usecaseimpl.UseCase;
import infra.mapper.project.CreateEmptyProjectMapper;
import web.controller.controllers.BaseController;

import java.util.UUID;

public class CreateEmptyProjectController extends BaseController<
        CreateEmptyProjectDTORequest,
        CreateEmptyProjectCommand,
        UUID,
        CreateEmptyProjectDTOResponse> {

    public CreateEmptyProjectController(UseCase<CreateEmptyProjectCommand, UUID> useCase) {
        super(useCase, CreateEmptyProjectMapper.INSTANCE);
    }

    @Override
    protected Class<CreateEmptyProjectDTORequest> getRequestClass() {
        return CreateEmptyProjectDTORequest.class;
    }
}
