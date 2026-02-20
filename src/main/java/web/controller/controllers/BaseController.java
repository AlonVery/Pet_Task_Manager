package web.controller.controllers;

import application.command.Command;
import application.usecase.usecaseimpl.UseCase;
import infra.mapper.Mapper;
import tools.jackson.databind.ObjectMapper;
import web.http.request.Request;

public abstract class BaseController<ReqDto, Cmd extends Command, Res, RespDto> {
    private final UseCase<Cmd, Res> useCase;
    private final Mapper<ReqDto, Cmd, Res, RespDto> mapper;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    protected BaseController(UseCase<Cmd, Res> useCase, Mapper<ReqDto, Cmd, Res, RespDto> mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    public RespDto handle(Request httpRequest) {
        ReqDto requestDto = readRequest(httpRequest);
        Cmd command = mapper.toCommand(requestDto);
        Res result = useCase.execute(command);
        return mapper.toResponse(result);
    }

    private ReqDto readRequest(Request httpRequest) {
        byte[] body = httpRequest.getBody();

        // 1) GET/DELETE и др. часто приходят без body
        if (body == null || body.length == 0) {
            return newEmptyRequestDtoOrNull();
        }

        // 2) Иногда body может быть "   \n" — это тоже считаем пустым
        String raw = new String(body, java.nio.charset.StandardCharsets.UTF_8);
        if (raw.trim().isEmpty()) {
            return newEmptyRequestDtoOrNull();
        }
        try {
            return jsonMapper.readValue(httpRequest.getBody(), getRequestClass());
        } catch (Exception e) {
            // Это правильно маппить на 400 на уровне handler’а,
            // но как минимум пробрасывайте IllegalArgumentException.
            throw new IllegalArgumentException("Invalid JSON request body", e);
        }
    }

    private ReqDto newEmptyRequestDtoOrNull() {
        try {
            return getRequestClass().getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            // Жёсткий, но честный вариант: если DTO нельзя создать без body,
            // значит для этого endpoint body обязателен.
            throw new IllegalArgumentException("Request body is empty, and "
                                               + getRequestClass().getSimpleName() +
                                               " has no no-args constructor. Either provide JSON body or use a different controller base for no-body requests.");
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to instantiate request DTO " + getRequestClass().getSimpleName(), e);
        }
    }

    protected abstract Class<ReqDto> getRequestClass();
}