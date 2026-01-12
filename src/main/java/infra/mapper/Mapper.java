package infra.mapper;

public interface Mapper<ReqDto, Cmd, Res, RespDto> {
    Cmd toCommand(ReqDto req);
    RespDto toResponse(Res result);
}
