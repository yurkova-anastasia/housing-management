package ru.clevertec.house.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;

import java.util.List;
import java.util.UUID;

public interface PersonServiceFacade {

    void create(PersonRequestDto personRequestDto);

    PersonResponseDto findById(UUID id);

    Page<HouseResponseDto> findOwnedHouses(UUID id,Pageable pageable);

    Page<PersonResponseDto> findAll(Pageable pageable);

    void update(UUID id, PersonRequestDto personRequestDto);

    void addOwnedHouse(UUID id, UUID houseId);

    void removeOwnedHouse(UUID id, UUID houseId);

    void deleteById(UUID id);
}
