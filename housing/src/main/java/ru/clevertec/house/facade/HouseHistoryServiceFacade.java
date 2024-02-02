package ru.clevertec.house.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;

import java.util.List;
import java.util.UUID;

public interface HouseHistoryServiceFacade {

    Page<PersonResponseDto> findAllPreviousResidentsOfHouse(UUID houseId, Pageable pageable);

    Page<PersonResponseDto> findAllPreviousOwnersOfHouse(UUID houseId, Pageable pageable);

    Page<HouseResponseDto> findAllPreviousResidencyOfPerson(UUID personId, Pageable pageable);

    Page<HouseResponseDto> findAllPreviousOwnedHousesOfPerson(UUID personId, Pageable pageable);

}
