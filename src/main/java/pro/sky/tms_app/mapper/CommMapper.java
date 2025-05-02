package pro.sky.tms_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pro.sky.tms_app.dto.CommDTO;
import pro.sky.tms_app.entity.CommEntity;

@Mapper(componentModel = "spring")
public interface CommMapper {

    CommMapper INSTANCE = Mappers.getMapper(CommMapper.class);

    CommDTO toDto(CommEntity commEntity);

    CommEntity toEntity(CommDTO commDTO);

}
