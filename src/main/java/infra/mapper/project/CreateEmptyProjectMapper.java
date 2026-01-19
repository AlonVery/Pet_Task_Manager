package infra.mapper.project;

import application.command.project_command.CreateEmptyProjectCommand;
import application.dto.in.project.CreateEmptyProjectDTORequest;
import application.dto.out.project.CreateEmptyProjectDTOResponse;
import infra.mapper.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@org.mapstruct.Mapper
public interface CreateEmptyProjectMapper extends Mapper<
        CreateEmptyProjectDTORequest,
        CreateEmptyProjectCommand,
        UUID,
        CreateEmptyProjectDTOResponse>
{
    CreateEmptyProjectMapper INSTANCE = Mappers.getMapper(CreateEmptyProjectMapper.class);

    @Override
    CreateEmptyProjectCommand toCommand(CreateEmptyProjectDTORequest createEmptyProjectDTORequest);

    @Override
    CreateEmptyProjectDTOResponse toResponse(UUID projectId);
}
