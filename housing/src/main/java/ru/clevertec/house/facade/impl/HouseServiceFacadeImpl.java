package ru.clevertec.house.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.HouseServiceFacade;
import ru.clevertec.house.mapper.HouseMapper;
import ru.clevertec.house.mapper.PersonMapper;
import ru.clevertec.house.model.House;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.service.PersonService;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HouseServiceFacadeImpl implements HouseServiceFacade {

    private final HouseService houseService;
    private final PersonService personService;
    private final HouseMapper houseMapper;
    private final PersonMapper personMapper;

    @Override
    public void create(HouseRequestDto houseRequestDto) {
        House house = houseMapper.toEntity(houseRequestDto);
        houseService.create(house);
    }

    @Override
    public HouseResponseDto findById(UUID id) {
        House house = houseService.findById(id);
        return houseMapper.toDto(house);
    }

    @Override
    public Page<PersonResponseDto> findHouseResidents(UUID id, Pageable pageable) {
        return personService.findPersonsByResidency(id, pageable)
                .map(personMapper::toDto);
    }

    @Override
    public Page<HouseResponseDto> findAll(Pageable pageable) {
        return houseService.findAll(pageable)
                .map(houseMapper::toDto);
    }

    @Override
    public void update(UUID id, HouseRequestDto houseRequestDto) {
        House house = houseMapper.toEntity(houseRequestDto);
        houseService.update(id, house);
    }

    @Override
    public void deleteById(UUID id) {
        houseService.deleteById(id);
    }
}
