package pro.sky.tms_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pro.sky.tms_app.dto.TaskDTO;
import pro.sky.tms_app.entity.TaskEntity;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDTO toDto(TaskEntity taskEntity);

    TaskEntity toEntity(TaskDTO DTO);

}
