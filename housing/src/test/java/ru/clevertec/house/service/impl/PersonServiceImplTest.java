package ru.clevertec.house.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.house.util.TestContainer;
import ru.clevertec.house.model.House;
import ru.clevertec.house.model.Person;
import ru.clevertec.house.repository.PersonRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static ru.clevertec.house.util.TestData.EXISTED_HOUSE_UUID;
import static ru.clevertec.house.util.TestData.EXISTED_PERSON_UUID;
import static ru.clevertec.house.util.TestData.getHouse;
import static ru.clevertec.house.util.TestData.getPerson;
import static ru.clevertec.house.util.TestData.getPersonWithId2;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PersonServiceImplTest extends TestContainer {

    private final PersonServiceImpl personService;

    @MockBean
    private final PersonRepository personRepository;

    @Test
    void create() {
        Person person = getPerson();
        doReturn(person)
                .when(personRepository).save(person);

        assertThatCode(() -> personService.create(person))
                .doesNotThrowAnyException();
    }

    @Test
    void findById() {
        Person expected = getPersonWithId2();
        doReturn(Optional.of(expected))
                .when(personRepository).findByUuid(expected.getUuid());

        Person actual = personService.findById(expected.getUuid());

        assertEquals(expected, actual);
    }

    @Test
    void findPersonsByResidency() {
        Person person = getPerson();
        Page<Person> expectedPage = new PageImpl<>(List.of(person));
        doReturn(expectedPage)
                .when(personRepository).findPersonsByResidency_Uuid(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        Page<Person> actualPage = personService.findPersonsByResidency(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        assertThat(actualPage).isEqualTo(expectedPage);
    }

    @Test
    void findAll() {
        Person person1 = getPerson();
        Person person2 = getPersonWithId2();
        Page<Person> expectedPage = new PageImpl<>(List.of(person1, person2));
        doReturn(expectedPage)
                .when(personRepository).findAll(PageRequest.of(0, 15));

        Page<Person> actualPage = personService.findAll(PageRequest.of(0, 15));

        assertThat(actualPage).isEqualTo(expectedPage);
    }

    @Test
    void findAllPreviousResidentsOfHouse() {
        Person person = getPerson();
        Page<Person> expectedPage = new PageImpl<>(List.of(person));
        doReturn(expectedPage)
                .when(personRepository).findAllPreviousResidentsOfHouse(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        Page<Person> actualPage = personService.findAllPreviousResidentsOfHouse(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        assertThat(actualPage).isEqualTo(expectedPage);
    }

    @Test
    void findAllPreviousOwnersOfHouse() {
        Person person = getPerson();
        Page<Person> expectedPage = new PageImpl<>(List.of(person));
        doReturn(expectedPage)
                .when(personRepository).findAllPreviousOwnersOfHouse(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        Page<Person> actualPage = personService.findAllPreviousOwnersOfHouse(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        assertThat(actualPage).isEqualTo(expectedPage);
    }

    @Test
    void update() {
        Person existingPerson = getPerson();
        Person updatedPerson = getPerson();
        updatedPerson.setName("new");
        doReturn(Optional.of(existingPerson))
                .when(personRepository).findByUuid(EXISTED_PERSON_UUID);
        doReturn(updatedPerson)
                .when(personRepository).save(existingPerson);

        Person result = personService.update(EXISTED_PERSON_UUID, updatedPerson);

        assertEquals(updatedPerson.getName(), result.getName());
        verify(personRepository).findByUuid(EXISTED_PERSON_UUID);
        verify(personRepository).save(existingPerson);
    }

    @Test
    void addOwnedHouse() {
        Person person = getPerson();
        House house = getHouse();
        Set<House> houses = new HashSet<>();
        houses.add(house);
        person.setOwnedHouses(houses);
        Person updatedPerson = getPerson();
        updatedPerson.setOwnedHouses(houses);
        updatedPerson.getOwnedHouses().add(house);
        doReturn(Optional.of(person))
                .when(personRepository).findByUuid(EXISTED_PERSON_UUID);
        doReturn(updatedPerson)
                .when(personRepository).save(updatedPerson);

        assertThatCode(() -> personService.addOwnedHouse(EXISTED_PERSON_UUID, house))
                .doesNotThrowAnyException();
        verify(personRepository).findByUuid(EXISTED_PERSON_UUID);
        verify(personRepository).save(person);
    }

    @Test
    void removeOwnedHouse() {
        Person person = getPerson();
        House house = getHouse();
        Set<House> houses = new HashSet<>();
        houses.add(house);
        person.setOwnedHouses(houses);
        Person updatedPerson = getPerson();
        updatedPerson.setOwnedHouses(houses);
        updatedPerson.getOwnedHouses().remove(house);
        doReturn(Optional.of(person))
                .when(personRepository).findByUuid(EXISTED_PERSON_UUID);
        doReturn(updatedPerson)
                .when(personRepository).save(updatedPerson);

        assertThatCode(() -> personService.removeOwnedHouse(EXISTED_PERSON_UUID, house))
                .doesNotThrowAnyException();
        verify(personRepository).findByUuid(EXISTED_PERSON_UUID);
        verify(personRepository).save(person);
    }

    @Test
    void deleteById() {
        assertThatCode(() -> personService.deleteById(EXISTED_PERSON_UUID))
                .doesNotThrowAnyException();

        verify(personRepository).deleteByUuid(EXISTED_PERSON_UUID);
    }
}