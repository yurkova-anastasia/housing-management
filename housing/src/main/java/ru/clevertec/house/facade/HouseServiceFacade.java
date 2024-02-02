package ru.clevertec.house.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;

import java.util.List;
import java.util.UUID;

public interface HouseServiceFacade {

    void create(HouseRequestDto houseRequestDto);

    HouseResponseDto findById(UUID id);

    Page<PersonResponseDto> findHouseResidents(UUID id, Pageable pageable);

    Page<HouseResponseDto> findAll(Pageable pageable);

    void update(UUID id, HouseRequestDto houseRequestDto);

    void deleteById(UUID id);

}
