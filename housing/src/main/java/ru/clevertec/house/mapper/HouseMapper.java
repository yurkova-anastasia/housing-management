package ru.clevertec.house.mapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.model.House;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonDeserialize(using = LocalDateTimeDeserializer.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = UUID.class)
public interface HouseMapper {

    HouseResponseDto toDto(House house);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    House toEntity(HouseRequestDto houseRequestDto);

    @AfterMapping
    default void setDates(@MappingTarget House house) {
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        house.setUuid(uuid);
        house.setCreateDate(now);
        house.setUpdateDate(now);
    }
}
