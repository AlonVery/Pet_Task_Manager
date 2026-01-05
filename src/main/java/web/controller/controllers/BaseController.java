package web.controller.controllers;

import application.command.Command;
import application.usecase.usecaseimpl.UseCase;
import infra.config.mapper.Mapper;
import tools.jackson.databind.ObjectMapper;

public abstract class BaseController<Req, Cmd extends Command, Res> {

    private final UseCase<Cmd, ?> useCase;
    private final Mapper<Req, Cmd, Res> mapper;
    private final ObjectMapper JsonMapper;

    protected BaseController(UseCase<Cmd, ?> useCase, Mapper<Req, Cmd, Res> mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
        JsonMapper = new ObjectMapper();
    }

    public String handle(String httpRequestBody) throws Exception {
        // 1. JSON → Request DTO
        Req request = JsonMapper.readValue(httpRequestBody, getRequestClass());

        // 2. Request DTO → Command
        Cmd command = mapper.toCommand(request);

        // 3. UseCase
        Object result = useCase.execute(command);

        // 4. Command / Domain → Response DTO
        Res response = mapper.toResponse(result);

        // 5. Response DTO → JSON
        return JsonMapper.writeValueAsString(response);
    }

    protected abstract Class<Req> getRequestClass();
}