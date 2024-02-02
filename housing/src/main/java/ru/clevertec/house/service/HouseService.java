package ru.clevertec.house.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.house.model.House;

import java.util.List;
import java.util.UUID;

public interface HouseService {

    House create(House house);

    House findById(UUID id);

    Page<House> findHousesByOwner(UUID id, Pageable pageable);

    Page<House> findAll(Pageable pageable);

    Page<House> findAllPreviousResidencyOfPerson(UUID personId, Pageable pageable);

    Page<House> findAllPreviousOwnedHousesOfPerson(UUID personId, Pageable pageable);

    House update(UUID id, House house);

    void deleteById(UUID id);
}
