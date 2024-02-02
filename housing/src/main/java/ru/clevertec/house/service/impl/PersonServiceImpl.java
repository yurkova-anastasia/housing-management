package ru.clevertec.house.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cachestarter.annotation.CustomCacheable;
import ru.clevertec.handling.exception.ServiceException;
import ru.clevertec.house.model.House;
import ru.clevertec.house.model.Person;
import ru.clevertec.house.repository.PersonRepository;
import ru.clevertec.house.service.PersonService;
import ru.clevertec.logging.annotation.Logging;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Logging
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    @Transactional
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    @CustomCacheable
    @Transactional(readOnly = true)
    public Person findById(UUID id) {
        return getById(id);
    }

    @Override
    public Page<Person> findPersonsByResidency(UUID id, Pageable pageable) {
        return personRepository.findPersonsByResidency_Uuid(id, pageable);
    }

    @Override
    @CustomCacheable
    @Transactional(readOnly = true)
    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Person> findAllPreviousResidentsOfHouse(UUID houseId, Pageable pageable) {
        return personRepository.findAllPreviousResidentsOfHouse(houseId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Person> findAllPreviousOwnersOfHouse(UUID houseId, Pageable pageable) {
        return personRepository.findAllPreviousOwnersOfHouse(houseId, pageable);
    }

    @Override
    @Transactional
    @CustomCacheable
    public Person update(UUID id, Person person) {
        Person existingPerson = getById(id);
        updatePerson(existingPerson, person);
        personRepository.save(existingPerson);
        return existingPerson;
    }

    @Override
    @Transactional
    public void addOwnedHouse(UUID id, House house) {
        Person person = getById(id);
        person.getOwnedHouses().add(house);
        personRepository.save(person);
    }

    @Override
    @Transactional
    public void removeOwnedHouse(UUID id, House house) {
        Person person = getById(id);
        person.getOwnedHouses().remove(house);
        personRepository.save(person);
    }

    @Override
    @Transactional
    @CustomCacheable
    public void deleteById(UUID id) {
        personRepository.deleteByUuid(id);
    }

    private void updatePerson(Person existingPerson, Person person) {
        if (person.getName() != null) {
            existingPerson.setName(person.getName());
        }
        if (person.getSurname() != null) {
            existingPerson.setSurname(person.getSurname());
        }
        if (person.getSex() != null) {
            existingPerson.setSex(person.getSex());
        }
        if (person.getPassportSeries() != null) {
            existingPerson.setPassportSeries(person.getPassportSeries());
        }
        if (person.getPassportNumber() != null) {
            existingPerson.setPassportNumber(person.getPassportNumber());
        }
        if (person.getResidency() != null) {
            existingPerson.setResidency(person.getResidency());
        }
    }

    private Person getById(UUID id) {
        return personRepository.findByUuid(id)
                .orElseThrow(() -> new ServiceException("Person was not found", HttpStatus.NOT_FOUND));
    }
}
