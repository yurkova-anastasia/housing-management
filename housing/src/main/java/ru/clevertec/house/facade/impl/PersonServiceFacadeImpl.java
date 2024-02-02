package ru.clevertec.house.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.PersonServiceFacade;
import ru.clevertec.house.mapper.HouseMapper;
import ru.clevertec.house.mapper.PersonMapper;
import ru.clevertec.house.model.House;
import ru.clevertec.house.model.Person;
import ru.clevertec.house.service.HouseService;
import ru.clevertec.house.service.PersonService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PersonServiceFacadeImpl implements PersonServiceFacade {

    private final PersonService personService;
    private final HouseService houseService;
    private final PersonMapper personMapper;
    private final HouseMapper houseMapper;

    @Override
    public void create(PersonRequestDto personRequestDto) {
        Person person = personMapper.toEntity(personRequestDto);
        personService.create(person);
    }

    @Override
    public PersonResponseDto findById(UUID id) {
        Person person = personService.findById(id);
        return personMapper.toDto(person);
    }

    @Override
    public Page<HouseResponseDto> findOwnedHouses(UUID id, Pageable pageable) {
        return houseService.findHousesByOwner(id, pageable)
                .map(houseMapper::toDto);
    }

    @Override
    public Page<PersonResponseDto> findAll(Pageable pageable) {
        return personService.findAll(pageable)
                .map(personMapper::toDto);
    }

    @Override
    public void update(UUID id, PersonRequestDto personRequestDto) {
        Person person = personMapper.toEntity(personRequestDto);
        personService.update(id, person);
    }

    @Override
    public void addOwnedHouse(UUID id, UUID houseId) {
        House house = houseService.findById(houseId);
        personService.addOwnedHouse(id, house);
    }

    @Override
    public void removeOwnedHouse(UUID id, UUID houseId) {
        House house = houseService.findById(houseId);
        personService.removeOwnedHouse(id, house);
    }

    @Override
    public void deleteById(UUID id) {
        personService.deleteById(id);
    }
}
