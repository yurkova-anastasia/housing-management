package ru.clevertec.house.mapper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import ru.clevertec.handling.exception.MappingException;
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.model.House;
import ru.clevertec.house.model.Person;
import ru.clevertec.house.repository.HouseRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonDeserialize(using = LocalDateTimeDeserializer.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class PersonMapper {

    @Autowired
    private HouseRepository houseRepository;

    public abstract PersonResponseDto toDto(Person person);

    @Mapping(target = "residency", source = "residencyId", qualifiedByName = "mapResidencyIdToHouse")
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    public abstract Person toEntity(PersonRequestDto personRequestDto);

    @Named("mapResidencyIdToHouse")
    protected House mapResidencyIdToHouse(UUID residencyId) throws EntityNotFoundException {
        return houseRepository.findByUuid(residencyId)
                .orElseThrow(() -> new MappingException("There is no house with this UUID", HttpStatus.NOT_FOUND));
    }

    @AfterMapping
    protected void setDates(@MappingTarget Person person) {
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        person.setUuid(uuid);
        person.setCreateDate(now);
        person.setUpdateDate(now);
    }

}
