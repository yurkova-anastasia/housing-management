package ru.clevertec.house.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.house.util.TestContainer;
import ru.clevertec.house.model.Person;

import java.util.List;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PersonRepositoryTest extends TestContainer {

    private final TestEntityManager testEntityManager;
    private final PersonRepository personRepository;

    @Test
    void findAllPreviousResidentsOfHouse() {
        Person person = testEntityManager.find(Person.class, 2L);
        Page<Person> expected = new PageImpl<>(List.of(person));

        Page<Person> actual = personRepository
                .findAllPreviousResidentsOfHouse(UUID.fromString("59c89acc-62a4-4cbc-87ed-78f632996c08"),
                        PageRequest.of(0, 15));

        Assertions.assertEquals(expected.getContent(), actual.getContent());
    }

    @Test
    void findAllPreviousOwnersOfHouse() {
        Person person = testEntityManager.find(Person.class, 2L);
        Page<Person> expected = new PageImpl<>(List.of(person));

        Page<Person> actual = personRepository
                .findAllPreviousOwnersOfHouse(UUID.fromString("59c89acc-62a4-4cbc-87ed-78f632996c08"),
                        PageRequest.of(0, 15));

        Assertions.assertEquals(expected.getContent(), actual.getContent());
    }
}