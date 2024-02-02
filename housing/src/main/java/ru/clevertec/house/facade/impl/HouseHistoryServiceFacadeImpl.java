package ru.clevertec.house.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.HouseHistoryServiceFacade;
import ru.clevertec.house.mapper.HouseMapper;
import ru.clevertec.house.mapper.PersonMapper;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.service.PersonService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HouseHistoryServiceFacadeImpl implements HouseHistoryServiceFacade {

    private final HouseService houseService;
    private final PersonService personService;
    private final HouseMapper houseMapper;
    private final PersonMapper personMapper;

    @Override
    public Page<PersonResponseDto> findAllPreviousResidentsOfHouse(UUID houseId, Pageable pageable) {
        return personService.findAllPreviousResidentsOfHouse(houseId, pageable)
                .map(personMapper::toDto);
    }

    @Override
    public Page<PersonResponseDto> findAllPreviousOwnersOfHouse(UUID houseId, Pageable pageable) {
        return personService.findAllPreviousOwnersOfHouse(houseId, pageable)
                .map(personMapper::toDto);
    }

    @Override
    public Page<HouseResponseDto> findAllPreviousResidencyOfPerson(UUID personId, Pageable pageable) {
        return houseService.findAllPreviousResidencyOfPerson(personId, pageable)
                .map(houseMapper::toDto);
    }

    @Override
    public Page<HouseResponseDto> findAllPreviousOwnedHousesOfPerson(UUID personId,Pageable pageable) {
        return houseService.findAllPreviousOwnedHousesOfPerson(personId, pageable)
                .map(houseMapper::toDto);
    }
}
