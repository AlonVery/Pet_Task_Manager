package infra.config.mapper;


public interface Mapper<Req, Cmd, Resp> {

    Cmd toCommand(Req req);

    Resp toResponse(Object object);
}
