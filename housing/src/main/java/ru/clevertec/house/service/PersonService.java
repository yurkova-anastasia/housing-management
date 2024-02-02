package ru.clevertec.house.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.house.model.House;
import ru.clevertec.house.model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    Person create(Person person);

    Person findById(UUID id);

    Page<Person> findPersonsByResidency(UUID id, Pageable pageable);

    Page<Person> findAll(Pageable pageable);

    Page<Person> findAllPreviousResidentsOfHouse(UUID houseId, Pageable pageable);

    Page<Person> findAllPreviousOwnersOfHouse(UUID houseId, Pageable pageable);

    Person update(UUID id, Person person);

    void addOwnedHouse(UUID id, House house);

    void removeOwnedHouse(UUID id, House house);

    void deleteById(UUID id);
}
